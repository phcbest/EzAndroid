package org.phcbest.ezandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.phcbest.ezandroid.demo.EzHttp.EzHttpActivity
import org.phcbest.ezimageselector.EzImageSelectorActivity


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

    override fun onClick(v: View?) {
        when (v) {
            mBtnEzhttp -> {
                startActivity(Intent(this@MainActivity, EzHttpActivity::class.java))
            }
            mBtnImageSelector -> {
                startActivity(Intent(this@MainActivity, EzImageSelectorActivity::class.java))
            }
            else -> {}
        }
    }
}