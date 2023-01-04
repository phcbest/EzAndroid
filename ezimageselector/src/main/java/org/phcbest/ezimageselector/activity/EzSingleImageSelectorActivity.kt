package org.phcbest.ezimageselector.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images.Media
import android.provider.MediaStore.Images.Media.DISPLAY_NAME
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.MediaColumns.DATE_ADDED
import android.util.Log
import android.view.View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import org.phcbest.ezimageselector.EzPhotoBean
import org.phcbest.ezimageselector.R
import org.phcbest.ezimageselector.SizeUtils
import org.phcbest.ezimageselector.apapter.PhotoAdapter
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date


open class EzSingleImageSelectorActivity : AppCompatActivity() {

    companion object {
        const val RESULT_CODE = 99999
        const val IMAGE_URI = "image_uri"

        private const val REQUEST_IMAGE_CAPTURE = 11999
        private val REQUEST_READ_STORE_PERMISSION_CODE = 0
        private val REQUEST_WRITE_STORE_PERMISSION_CODE = 1
        private const val TAG = "EzImageSelectorActivity"

        //边距
        var mMargin: Int = 3
    }

    private val mIvBack: ImageView by lazy { findViewById(R.id.iv_back) }

    //    private val mHsvCategory: HorizontalScrollView by lazy { findViewById<HorizontalScrollView>(R.id.hsv_category) }
    private val mLlCategory: LinearLayout by lazy { findViewById(R.id.ll_category) }
    private val mRvImagePreview: RecyclerView by lazy { findViewById(R.id.rv_image_preview) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_image_selector)
        //
        mIvBack.setOnClickListener {
            finish()
        }
        (mRvImagePreview.layoutParams as MarginLayoutParams).marginStart = mMargin
        (mRvImagePreview.layoutParams as MarginLayoutParams).marginEnd = mMargin
        //隐藏活动栏
        supportActionBar?.hide()
        //沉淀状态栏和导航栏
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.statusBarColor = Color.parseColor("#FFFFFF")
        window.decorView.systemUiVisibility = SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility =
                SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.navigationBarColor = Color.parseColor("#FFFFFF")
        }

        //没有权限直接申请,用户不同意直接退了
        if (doCheckPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_READ_STORE_PERMISSION_CODE
            )
        ) {
            //扫描相册所有照片
            scanAlbum()

            //分类照片
            adapterCategory()

            //ui显示
            showUI()
        }
    }

    private var mAllPhotoList = mutableListOf<EzPhotoBean>()
    private var mPhotoCategoryMap = mutableMapOf<String, List<EzPhotoBean>>()


    /**
     * 加锁防止幻读
     */
    @Synchronized
    private fun scanAlbum() {
        val contentUri = EXTERNAL_CONTENT_URI
        val query = contentResolver.query(
            contentUri, arrayOf(
                Media.DATA, DISPLAY_NAME, Media.DATE_ADDED, Media._ID, Media.MIME_TYPE, Media.SIZE
            ), MediaStore.MediaColumns.SIZE + ">0", null, "$DATE_ADDED DESC"
        )
        val starTime = System.currentTimeMillis()
        query?.let {
            while (it.moveToNext()) {
                val data = it.getString(0)
                val displayName = it.getString(1)
                val dateAdded = it.getString(2)
                val id = it.getString(3)
                val mimeType = it.getString(4)
                val size = it.getString(5)
                Log.i(
                    TAG,
                    "scanAlbum [path: $data] \t [displayName: $displayName] \t [dateAdded: $dateAdded] \t [id: $id] \t [mimeType: $mimeType] \t [size: $size]"
                )
                mAllPhotoList.add(
                    EzPhotoBean(
                        path = data,
                        displayName = displayName,
                        dateAdded = dateAdded,
                        id = id,
                        mimeType = mimeType,
                        size = size
                    )
                )
            }
        }
        Log.i(
            TAG,
            "scanAlbum: 读取图片完成,耗时${System.currentTimeMillis() - starTime} 索引图片共${mAllPhotoList.size}张"
        )

        query?.close()
    }

    /**
     * 检查文件读权限
     * @return 返回当前权限状态,true为有权限,false无权限
     */
    private fun doCheckPermission(permission: String, requestCode: Int): Boolean {
        val storagePermission = ContextCompat.checkSelfPermission(this, permission)
        if (storagePermission == PackageManager.PERMISSION_DENIED) {
            requestPermissions(
                arrayListOf(permission).toTypedArray(), requestCode
            )
        }
        return storagePermission != PackageManager.PERMISSION_DENIED
    }

    private var mCategoryItemList = mutableListOf<TextView>()
    private var mTakePictureUri: String? = null

    /**
     * 根据获得的照片显示UI
     */
    private fun showUI() {
        for (key in mPhotoCategoryMap.keys) {
            val textView = TextView(this).apply {
                val marginLayoutParams = ViewGroup.MarginLayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                marginLayoutParams.setMargins(
                    SizeUtils.dp2px(this@EzSingleImageSelectorActivity, 5F),
                    SizeUtils.dp2px(this@EzSingleImageSelectorActivity, 5F),
                    SizeUtils.dp2px(this@EzSingleImageSelectorActivity, 5F),
                    SizeUtils.dp2px(this@EzSingleImageSelectorActivity, 5F)
                )
                this.setPadding(
                    SizeUtils.dp2px(this@EzSingleImageSelectorActivity, 15F),
                    SizeUtils.dp2px(this@EzSingleImageSelectorActivity, 5F),
                    SizeUtils.dp2px(this@EzSingleImageSelectorActivity, 15F),
                    SizeUtils.dp2px(this@EzSingleImageSelectorActivity, 5F)
                )
                this.layoutParams = marginLayoutParams

                setSelectStyle(this, false)

                this.textSize = SizeUtils.sp2px(this@EzSingleImageSelectorActivity, 6F).toFloat()
                this.text = key
                if (key.contains("ezall")) {
                    this.text = context.getString(R.string.all_photo)
                }
            }
            mCategoryItemList.add(textView)
        }
        //适配选择列表
        val photoAdapter = object : PhotoAdapter(mPhotoCategoryMap) {
            override fun onSelect(position: Int, ezPhotoBeans: List<EzPhotoBean>?) {
                if (position == 0) {
                    //检查是否有文件写入权限
                    if (!doCheckPermission(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            REQUEST_WRITE_STORE_PERMISSION_CODE
                        )
                    ) {
                        return
                    }
                    //跳转到相机
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        takePictureIntent.resolveActivity(packageManager)?.also {
                            val photoFile: File? = try {
                                createImageFile()
                            } catch (ex: IOException) {
                                Toast.makeText(
                                    this@EzSingleImageSelectorActivity,
                                    getString(R.string.takePhotoError),
                                    Toast.LENGTH_SHORT
                                ).show()
                                null
                            }
                            photoFile?.also {
                                val photoURI = FileProvider.getUriForFile(
                                    this@EzSingleImageSelectorActivity,
                                    "${this@EzSingleImageSelectorActivity.packageName}.fileprovider",
                                    it
                                )
                                mTakePictureUri = photoFile.absolutePath
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                            }
                        }
                    }
                } else {
                    //选择了图片,可以退出activity
                    ezPhotoBeans?.let {
                        val ezPhotoBean = it[position - 1]
                        exitActivityWithSetResult(ezPhotoBean.path)
                    }
                }
            }
        }
        mRvImagePreview.layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        mRvImagePreview.adapter = photoAdapter


        //适配分类列表
        mCategoryItemList.forEachIndexed { index, it ->
            if (index == 0) {
                setSelectStyle(it, true)
            }
            mLlCategory.addView(it)
            it.setOnClickListener { nowView ->
                mCategoryItemList.forEach { v ->
                    setSelectStyle(v, false)
                    if (nowView == v) {
                        setSelectStyle(v, true)
                    }
                }
                //切换分类
                photoAdapter.switchCategory(mCategoryItemList.indexOf(nowView))
                mRvImagePreview.scrollToPosition(0)
            }
        }
    }

    private fun setSelectStyle(it: TextView, isSelect: Boolean) {
        if (isSelect) {
            it.background = ResourcesCompat.getDrawable(
                resources, R.drawable.btn_bg_category_text_select, theme
            )
            it.setTextColor(Color.parseColor("#FF39AFFB"))
        } else {
            it.background = ResourcesCompat.getDrawable(
                resources, R.drawable.btn_bg_category_text, theme
            )
            it.setTextColor(Color.parseColor("#494949"))
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_READ_STORE_PERMISSION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    finish()
                } else {
                    scanAlbum()
                    adapterCategory()
                    showUI()
                }
            }
            REQUEST_WRITE_STORE_PERMISSION_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "需要文件存储权限来存储您的照片", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {}
        }
    }

    /**
     * 适配图片文件夹列表,将获得的图片按文件夹拆分
     */
    private fun adapterCategory() {
        if (mAllPhotoList.size == 0) {
            Toast.makeText(this, "相册为空", Toast.LENGTH_SHORT).show()
            return
        }
        mAllPhotoList.forEachIndexed { index, ezPhotoBean ->
            val path = ezPhotoBean.path
            val photoFolder = getPhotoFolder(path)
            ezPhotoBean.folderName = photoFolder
        }
        //通过不同的文件夹进行分类
        val map = mAllPhotoList.groupBy {
            it.folderName
        }
        mPhotoCategoryMap["ezall"] = mAllPhotoList
        mPhotoCategoryMap.putAll(map)
        Log.i(TAG, "adapterCategory 分组结果: $map")
    }

    /**
     * 获得照片处在的文件夹
     */
    private fun getPhotoFolder(path: String): String {
        if (path.isEmpty()) {
            return ""
        }
        val strings = path.split(File.separator)
        if (strings.size <= 2) {
            return ""
        }
        return strings[strings.size - 2]
    }

    private lateinit var currentPhotoPath: String

    /**
     * 创建Image的文件夹
     */
    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storeDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storeDir).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            mTakePictureUri?.let {
                Log.i(TAG, "onActivityResult: 拍照获得的URI $it")
                exitActivityWithSetResult(it)
            }
        }
    }


    private fun exitActivityWithSetResult(uri: String) {
        intent.putExtra(IMAGE_URI, uri)
        setResult(RESULT_CODE, intent)
        finish()
    }

}