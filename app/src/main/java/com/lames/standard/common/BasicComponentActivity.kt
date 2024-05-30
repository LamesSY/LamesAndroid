package com.lames.standard.common

import android.content.res.Configuration
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity

abstract class BasicComponentActivity : ComponentActivity() {

    protected fun setTransparentStatusBar(useDarkStatusBarText: Boolean = true) {
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

}