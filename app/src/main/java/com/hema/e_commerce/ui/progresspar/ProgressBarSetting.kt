package com.hema.e_commerce.ui.progresspar

import android.app.Activity
import android.content.Context
import android.os.Handler

class ProgressBarSetting {
    fun setProgress(context: Activity){

        val loading = ProgressHandler(context)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object :Runnable{
            override fun run() {
                loading.isDismiss()
            }

        },500)

    }


}