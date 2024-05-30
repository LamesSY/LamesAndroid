package com.lames.standard.module.launch

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lames.standard.R
import com.lames.standard.common.BasicComponentActivity
import com.lames.standard.common.CommonActivity
import com.lames.standard.databinding.ActivitySplashBinding
import com.lames.standard.module.home.HomeActivity
import com.lames.standard.tools.LogKitX
import com.lames.standard.toolsAndroid.forColor
import com.lames.standard.toolsAndroid.forString
import com.lames.standard.toolsAndroid.onClick
import com.lames.standard.toolsAndroid.start
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : BasicComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()
        setContent {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().background(Color(forColor(R.color.md_theme_primaryContainer)))
            ) {
                Spacer(modifier = Modifier.weight(0.2f))
                SplashLogo(R.mipmap.ic_app_logo)
                SplashTitle(R.string.app_name)
                Spacer(modifier = Modifier.weight(1f))
            }
            LaunchedEffect(Unit) {
                delay(555)
                start(HomeActivity::class.java)
            }
        }
    }

}

@Composable
private fun SplashLogo(resId: Int) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = "appLogo",
        modifier = Modifier
            .fillMaxWidth(0.33f)
            .clip(RoundedCornerShape(15.dp))

    )
}

@Composable
private fun SplashTitle(resId: Int) {
    Text(
        text = forString(resId),
        modifier = Modifier.padding(top = 10.dp),
        fontSize = 22.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = Color(forColor(R.color.md_theme_primary))
    )
}
