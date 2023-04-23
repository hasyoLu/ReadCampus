package com.example.readcampus.db

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.readcampus.base.BaseApplication.Companion.getContext

/**
 * author : Haa-zzz
 * time : 2021/8/1
 *
 * 使用 SharePreferences 来存储登录状态 和 登录的phone
 *
 * isLogin 用于判断是进入LoginActivity,还是直接跳转到主页面
 * phone 用于后面从Room或者BMob中获取数据使用
 *
 * [saveToSp]会在按下登录按钮，通过BMob验证成功后调用
 *
 */
//第一步：获取Sharedpreferences对象
val sharedPreferences: SharedPreferences = getContext().getSharedPreferences("hor", MODE_PRIVATE)
fun saveToSp( phone : String , isLogin : Boolean){
    //第二步：获取SharedPreferences.Editor对象
    val editor = sharedPreferences.edit()
    //第三步，添加数据
    editor.putString("number", phone)
    editor.putBoolean("isLogin",isLogin)
    //第四步：提交
    editor.apply()
}
fun querySpIsLogin() : Boolean{
    return sharedPreferences.getBoolean("isLogin",false)
}
fun querySpNumber() : String?{
    return sharedPreferences.getString("number",null)
}