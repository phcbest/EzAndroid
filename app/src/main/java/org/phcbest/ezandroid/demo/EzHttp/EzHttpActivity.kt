package org.phcbest.ezandroid.demo.EzHttp

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.phcbest.ezandroid.R
import org.phcbest.ezhttp.EzHttp

private const val TAG = "EzHttpActivity"

class EzHttpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ez_http)
        val ezHttp = EzHttp.newInstance()

        ezHttp.buildRetrofit("https://api.bilibili.com")
        val api = ezHttp.getApi(NetApi::class.java)

        //弱网检测
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        val detailedState = info!!.detailedState
        if (detailedState == NetworkInfo.DetailedState.VERIFYING_POOR_LINK) {
            Log.i(TAG, "onCreate: 弱网")
        } else {
            Log.i(TAG, "onCreate: 非弱网")
        }

        lifecycleScope.launch {
            val userInfo = api?.getUserInfo()
        }
    }
}