package org.phcbest.ezhttp

import java.util.logging.Logger


internal object HttpLogger {

    private val global = Logger.getGlobal()


    fun logOther(logType: LogType) {
        when (logType) {
            LogType.REQUEST -> global.info("┌────── Request ────────────────────────────────────────────────────────────────────────")
            LogType.RESPONSE -> global.info("┌────── Response ───────────────────────────────────────────────────────────────────────")
            LogType.END -> global.info("└───────────────────────────────────────────────────────────────────────────────────────")
        }
    }

    fun logContent(name: String, content: String) {
        global.info("|${name}:${content}")
    }

    enum class LogType {
        REQUEST, RESPONSE, END
    }
}