package org.phcbest.ezandroid.demo.EzImageSelect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import org.phcbest.ezandroid.R
import org.phcbest.ezimageselector.EzImageLauncher
import org.phcbest.ezimageselector.activity.EzSingleImageSelectorActivity

private const val TAG = "EzISActivity"

class EzISActivity : AppCompatActivity() {

    private val mIvIv: ImageView by lazy { findViewById<ImageView>(R.id.iv_iv) }
    private val mBtnSingleSelect: Button by lazy { findViewById<Button>(R.id.btn_single_select) }
    private val mBtnMultipleSelect: Button by lazy { findViewById<Button>(R.id.btn_multiple_select) }

    private var registerForActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != EzSingleImageSelectorActivity.RESULT_CODE) {
            return@registerForActivityResult
        }
        val data: Intent? = result.data
        val uri = data?.getStringExtra(EzSingleImageSelectorActivity.IMAGE_URI)
        Log.i(
            TAG, "startSingleImageSelect: 成功获得图片回调${uri}"
        )
        uri?.let {
            Glide.with(applicationContext).load(it).into(mIvIv)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ez_isactivity)
        mBtnSingleSelect.setOnClickListener {
            EzImageLauncher.startSingleImageSelect(
                registerForActivityResult, this
            )
        }

        mBtnMultipleSelect.setOnClickListener {

        }
    }
}