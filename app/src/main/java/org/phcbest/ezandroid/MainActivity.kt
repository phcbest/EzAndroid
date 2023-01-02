package org.phcbest.ezandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import org.phcbest.ezandroid.demo.EzHttp.EzHttpActivity
import org.phcbest.ezimageselector.EzImageLauncher

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val mBtnEzhttp: Button by lazy { findViewById<Button>(R.id.btn_ezhttp) }
    private val mBtnImageSelector: Button by lazy { findViewById<Button>(R.id.btn_image_selector) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnList = mutableListOf<Button>(mBtnEzhttp, mBtnImageSelector)

        btnList.forEach {
            it.setOnClickListener(this)
        }
    }

    private var registerForActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.i(
            TAG, "startSingleImageSelect: 成功获得图片回调"
        )
        val data: Intent? = result.data
    }

    override fun onClick(v: View?) {
        when (v) {
            mBtnEzhttp -> {
                startActivity(Intent(this@MainActivity, EzHttpActivity::class.java))
            }
            mBtnImageSelector -> {
                EzImageLauncher.startSingleImageSelect(
                    registerForActivityResult, this
                )
            }
            else -> {}
        }
    }
}