package com.example.readcampus.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.readcampus.utils.LogUtil

abstract class BaseFragment: Fragment() {
    companion object {
        private val  TAG =  lazy { this.toString() }.value
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtil.i(TAG, "onCreate", savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogUtil.i(TAG, "onCreateView", view, savedInstanceState)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 创建完成事件3
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogUtil.i(TAG, "onViewCreated", view, savedInstanceState)
        initView()
        initListener()
        initData()
    }

    open fun initData(){}

    open fun initListener(){}

    open fun initView(){}

    /**
     * 恢复
     */
    override fun onResume() {
        super.onResume()
        LogUtil.i(TAG, "onResume")
    }

    /**
     * 保存状态
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        LogUtil.i(TAG, "onSaveInstanceState", outState)
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
     * 销毁
     */
    override fun onDestroy() {
        super.onDestroy()
        LogUtil.i(TAG, "onDestroy")
        //mCompositeDisposables.clear()
    }
}