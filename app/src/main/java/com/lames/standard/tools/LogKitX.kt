package com.lames.standard.tools

import android.util.Log
import com.lames.standard.BuildConfig
import com.lames.standard.common.GlobalVar
import java.io.File
import java.io.FileWriter

object LogKitX {

    init {
        Log.d("DEF_TAG", "LogKit is init")
    }

    private const val DEF_TAG = "LogKit"

    private const val MAX_NUM = 4000

    private var isEnable = BuildConfig.DEBUG

    var saveToFile: Boolean = false

    fun v(tag: String = DEF_TAG, msg: String) = printLog(Log.VERBOSE, tag, msg)
    fun d(tag: String = DEF_TAG, msg: String) = printLog(Log.DEBUG, tag, msg)
    fun i(tag: String = DEF_TAG, msg: String) = printLog(Log.INFO, tag, msg)
    fun w(tag: String = DEF_TAG, msg: String) = printLog(Log.WARN, tag, msg)
    fun e(tag: String = DEF_TAG, msg: String) = printLog(Log.ERROR, tag, msg)

    private fun detail(): String? {
        val stackTrace = Thread.currentThread().stackTrace
        stackTrace.forEach {
            if (it.isNativeMethod) return@forEach
            if (it.className == Thread::class.java.name) return@forEach
            if (it.className == LogKitX::class.java.name) return@forEach
            return "[(${it.fileName}:${it.lineNumber}) ${it.methodName}]"
        }
        return null
    }

    private fun printLog(type: Int, tag: String = DEF_TAG, message: String) {
        if (isEnable.not()) return
        val msg = detail()?.let { d -> "$d -> $message" } ?: message
        if (saveToFile) saveToFile(tag, msg)
        when (type) {
            Log.VERBOSE -> msg.chunked(MAX_NUM).forEach { Log.v(tag, it) }
            Log.DEBUG -> msg.chunked(MAX_NUM).forEach { Log.d(tag, it) }
            Log.INFO -> msg.chunked(MAX_NUM).forEach { Log.i(tag, it) }
            Log.WARN -> msg.chunked(MAX_NUM).forEach { Log.w(tag, it) }
            Log.ERROR -> msg.chunked(MAX_NUM).forEach { Log.e(tag, it) }
        }
    }

    private fun saveToFile(tag: String, msg: String) = runCatching {
        val now = System.currentTimeMillis()
        val content = StringBuilder()
            .append(now.toPatternString("yyyyMMdd-HH:mm:ss:SSS"))
            .append("|")
            .append(tag)
            .append("|")
            .append(msg)
            .append("\n")
        val file = File(GlobalVar.obtain().appRootPath + File.separator + "log_${now.toPatternString("yyyyMMdd")}.txt")
        synchronized(LogKitX::class.java) {
            FileWriter(file, true).use { it.write(content.toString()) }
        }

    }

}