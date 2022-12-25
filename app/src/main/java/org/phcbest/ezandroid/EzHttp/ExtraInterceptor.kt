package org.phcbest.ezhttp

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 用来对请求头添加额外参数的
 */
internal class ExtraInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        TODO()
    }
}