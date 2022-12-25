package org.phcbest.ezandroid.demo.EzHttp

import retrofit2.http.GET

interface NetApi {


    @GET("/x/space/wbi/acc/info?mid=5408366")
    suspend fun getUserInfo(): BiliBiliDemoBean
}