package com.lames.standard.common

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class CommonActivity<T : ViewBinding> : AppCompatActivity() {

    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setTransparentStatusBar(true)
        binding = getViewBinding()
        setContentView(binding.root)
        initialization()
        bindEvent()
        doExtra()
    }

    protected abstract fun getViewBinding(): T

    protected open fun initialization() {}
    protected open fun bindEvent() {}
    protected open fun doExtra() {}

    protected fun setTransparentStatusBar(useDarkStatusBarText: Boolean) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        //如果是黑夜模式则isLightStatusBar要取反
        var isDarkStatusBarText = useDarkStatusBarText
        if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES) {
            isDarkStatusBarText = !isDarkStatusBarText
        }
        window.decorView.apply {
            systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            systemUiVisibility = if (isDarkStatusBarText) systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        }
    }


    /*override fun dispatchTouchEvent(mv: MotionEvent?): Boolean {
        mv ?: return super.dispatchTouchEvent(mv)
        if (mv.action == MotionEvent.ACTION_DOWN) {
            val focusView = currentFocus
            if (isViewBeingTouched(focusView, mv)) hideKeyboard()
        }
        return super.dispatchTouchEvent(mv)
    }*/

}