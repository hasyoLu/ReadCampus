package com.example.readcampus.base

import android.app.Application
import android.content.Context
import cn.bmob.v3.Bmob

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        context = this
        Bmob.initialize(this, "c9cdfcef07b2af32b45776233231a8aa")
    }

    companion object{
        private lateinit var context: BaseApplication
        @JvmStatic
        fun getContext() : Context {
            return context
        }
    }

}