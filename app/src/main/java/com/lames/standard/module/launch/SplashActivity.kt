package com.lames.standard.module.launch

import android.annotation.SuppressLint
import android.view.LayoutInflater
import com.lames.standard.R
import com.lames.standard.common.CommonActivity
import com.lames.standard.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : CommonActivity<ActivitySplashBinding>() {

    override fun getViewBinding() = ActivitySplashBinding.inflate(LayoutInflater.from(this))

    override fun initialization() {

    }
}