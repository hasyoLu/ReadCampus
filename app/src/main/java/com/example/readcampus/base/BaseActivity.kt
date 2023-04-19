package com.example.readcampus.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.readcampus.R
import com.example.readcampus.utils.LogUtil
import com.example.readcampus.utils.StatusBarUtil

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        LogUtil.i(TAG, "onCreate", savedInstanceState)
        setContentView(getRLayout())
        StatusBarUtil.setWindowStatusBarColor(this, R.color.main_color)

        initView()
        initListener()
        initData()
    }

    /**
     * 恢复保存状态
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        LogUtil.i(TAG, "onRestoreInstanceState1", savedInstanceState)
    }

    /**
     * 恢复保存状态
     */
    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        LogUtil.i(TAG, "onRestoreInstanceState2", savedInstanceState, persistentState)
    }

    /**
     * 活动启动完成时调用
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        LogUtil.i(TAG, "onPostCreate1", savedInstanceState)
    }

    /**
     * 活动启动完成时调用
     */
    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        LogUtil.i(TAG, "onPostCreate2", savedInstanceState, persistentState)
    }

    /**
     * 设置主容器
     */
    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        LogUtil.i(TAG, "setContentView", layoutResID)
    }

    /**
     * 暂停
     */
    override fun onPause() {
        super.onPause()
        LogUtil.i(TAG, "onPause")
    }

    /**
     * 停止
     */
    override fun onStop() {
        super.onStop()
        LogUtil.i(TAG, "onStop")
    }

    /**
     * 存储状态
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.i(TAG, "onSaveInstanceState1", outState)
    }

    /**
     * 存储状态
     */
    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        LogUtil.i(TAG, "onSaveInstanceState2", outState, outPersistentState)
    }

    /**
     * 销毁
     */
    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(TAG, "onDestroy")
    }


    open fun initData(){}

    open fun initListener(){}

    open fun initView(){}

    abstract fun getRLayout(): Int

    companion object {
        private var TAG: String = lazy { this::class.java.simpleName }.value
    }
}