package com.lames.standard.module.home

import android.view.LayoutInflater
import com.lames.standard.R
import com.lames.standard.common.CommonActivity
import com.lames.standard.databinding.ActivityHomeBinding

class HomeActivity : CommonActivity<ActivityHomeBinding>() {

    override fun getViewBinding(): ActivityHomeBinding = ActivityHomeBinding.inflate(LayoutInflater.from(this))


}