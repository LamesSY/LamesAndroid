package com.lames.standard.toolsAndroid

import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.lames.standard.common.CommonApp
import com.lames.standard.common.CommonDialogFragment
import kotlinx.coroutines.Job
import java.text.SimpleDateFormat
import java.util.Locale

fun changeTimeToStamp(parseTime: String, pattern: String): Long {
    return try {
        val parse = SimpleDateFormat(pattern, Locale.getDefault()).parse(parseTime)
        parse?.time ?: 0
    } catch (e: Exception) {
        e.printStackTrace()
        0
    }
}

/**
 * 获取字符串简便方法
 */
fun forString(resId: Int): String = CommonApp.obtain<CommonApp>().getString(resId)

/**
 * 获取颜色值简便方法
 */
fun forColor(resId: Int): Int = ContextCompat.getColor(CommonApp.obtain(), resId)

/**
 * 安全取消任务
 */
fun Job?.safeCancel() {
    this ?: return
    if ((this.isActive && this.isCancelled.not())) this.cancel()
}

/**
 * 任务是否正在执行
 */
fun Job?.isRunning(): Boolean {
    this ?: return false
    return this.isActive && this.isCancelled.not()
}

fun View.alwaysGetGesture() {
    this.setOnTouchListener { v, event ->
        if (v == this) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> v.parent.requestDisallowInterceptTouchEvent(true)
                MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
            }
        }
        v.performClick()
        return@setOnTouchListener false
    }
}

inline fun <reified T : CommonDialogFragment<*>> showDialogFragment(fragmentManager: FragmentManager, tag: String? = null, initDialog: ((T) -> Unit) = {}) {
    tag?.let { if (fragmentManager.findFragmentByTag(it) != null) return }
    val d = T::class.java.getDeclaredConstructor().newInstance()
    initDialog.invoke(d)
    d.show(fragmentManager, tag)
}