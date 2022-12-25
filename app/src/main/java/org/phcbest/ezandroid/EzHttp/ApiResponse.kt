package org.phcbest.ezandroid.ezhttp

sealed class ApiResponse {
    // 正常响应情况调用方不需要 errcode, msg
    data class Ok<T>(
        val data: T
    )

    data class BizError<T>(
        val errcode: Int, val msg: String
    ) : ApiResponse()

    data class OtherError<T>(
        val throwable: Throwable
    ) : ApiResponse()
}