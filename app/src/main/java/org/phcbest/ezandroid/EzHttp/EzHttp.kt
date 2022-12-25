package org.phcbest.ezhttp

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class EzHttp {
    companion object {

        private var ezHttp: EzHttp? = null
            get() {
                if (field == null) {
                    field = EzHttp()
                }
                return field
            }

        @Synchronized
        fun newInstance(): EzHttp {
            return ezHttp!!
        }
    }

    private var okHttpClient = OkHttpClient.Builder().connectTimeout(3000, TimeUnit.MILLISECONDS)
        .readTimeout(3000, TimeUnit.MILLISECONDS).writeTimeout(3000, TimeUnit.MILLISECONDS)
//        .addInterceptor(ExtraInterceptor())
        .addInterceptor(LoggerInterceptor())
        .build()

    private var retrofit: Retrofit? = null


    private var moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * 创建一个Retrofit对象
     */
    fun buildRetrofit(baseUrl: String) {
        retrofit =
            Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(okHttpClient)
                .build()
    }

    /**
     * 获得网络请求的Api
     */
    fun <T> getApi(clazz: Class<T>): T? {
        if (retrofit == null) {
            HttpLogger.logContent("ERROR", "don't have buildRetrofit")
            return null
        }
        return retrofit!!.create(clazz)
    }


}