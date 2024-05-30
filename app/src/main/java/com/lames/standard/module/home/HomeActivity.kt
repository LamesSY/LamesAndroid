package com.lames.standard.module.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lames.standard.R
import com.lames.standard.common.BasicComponentActivity
import com.lames.standard.toolsAndroid.forColor

class HomeActivity : BasicComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransparentStatusBar()
        setContent {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().background(Color(forColor(R.color.md_theme_primary)))
            ) {
                Spacer(modifier = Modifier.weight(0.3f))
                Image(
                    painter = painterResource(id = R.mipmap.ic_app_logo), contentDescription = "appLogo", modifier = Modifier
                        .padding(top = 30.dp)
                        .size(85.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.weight(1.2f))
            }
        }

    }


}