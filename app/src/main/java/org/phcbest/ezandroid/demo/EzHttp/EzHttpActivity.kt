package org.phcbest.ezandroid.demo.EzHttp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.phcbest.ezandroid.R
import org.phcbest.ezhttp.EzHttp

class EzHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ez_http)
        val ezHttp = EzHttp.newInstance()

        ezHttp.buildRetrofit("https://api.bilibili.com")
        val api = ezHttp.getApi(NetApi::class.java)

        lifecycleScope.launch {
            val userInfo = api?.getUserInfo()
        }
    }
}