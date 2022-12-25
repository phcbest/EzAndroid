package org.phcbest.ezhttp

import okhttp3.Interceptor
import okhttp3.Response

internal class LoggerInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBody = request.body()

        HttpLogger.logOther(HttpLogger.LogType.REQUEST)

        HttpLogger.logContent("Method", request.method())
        HttpLogger.logContent("RequestURL", request.url().toString())
        val headers = request.headers()
        for (i in 0 until headers.size()) {
            HttpLogger.logContent(headers.name(i), headers.value(i))
        }

        HttpLogger.logOther(HttpLogger.LogType.END)

        val response = chain.proceed(request)

        return response
    }
}