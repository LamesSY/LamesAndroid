package com.lames.standard

import com.lames.standard.common.CommonApp
import com.lames.standard.common.GlobalVar
import com.lames.standard.network.HttpKit
import com.lames.standard.toolsAndroid.AppKit
import com.lames.standard.toolsAndroid.ToastKit

class App : CommonApp() {

    override fun doCommonInit() {
        initHttp()
        initLoadSir()
        ToastKit.init(this)
        GlobalVar.obtain()
        AppKit.obtain().init(this)
    }

    private fun initHttp() {
        HttpKit.initHttpConfig()
    }

    private fun initLoadSir() {
        /*LoadSir.beginBuilder()
            .addCallback(EmptyCallback())
            .addCallback(ErrorCallback())
            .addCallback(LoadingCallback())
            .commit()*/
    }

    override fun onSignOut() {
        AppKit.obtain().restartApp(this)
    }
}