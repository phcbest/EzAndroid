package org.phcbest.ezandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.phcbest.ezandroid.demo.EzHttp.NetApi
import org.phcbest.ezhttp.EzHttp


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ezHttp = EzHttp.newInstance()

        ezHttp.buildRetrofit("https://api.bilibili.com")
        val api = ezHttp.getApi(NetApi::class.java)

        lifecycleScope.launch{
            val userInfo = api?.getUserInfo()
        }
    }
}