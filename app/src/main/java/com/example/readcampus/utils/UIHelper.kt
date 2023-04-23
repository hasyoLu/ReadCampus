package com.example.readcampus.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.annotation.RequiresApi


/**
 * author : Haa-zzz
 * time : 2021/8/1
 * 一些UI相关的帮助类
 */
/*
    监听EditView改变
 */
fun EditText.afterTextChanged(afterTextChanged : (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
/*
 * 动态修改标题栏的颜色
 *
 * @param activity   Activity
 * @param colorResId 颜色ResourceID
 */
@RequiresApi(Build.VERSION_CODES.M)
fun setWindowStatusBarColor(activity: Activity, colorResId: Int) {
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            // 顶部状态栏
            window.statusBarColor = activity.resources.getColor(colorResId,null)
            // 底部导航栏
            // window.setNavigationBarColor(activity.getResources().getColor(colorResId));
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
/*
Dialog 弹出
 */

//fun dialogBtw(view: View, context: Context): Dialog {
//    val dialog = Dialog(context, R.style.DialogTheme)
//    dialog.setContentView(view)
//    val window = dialog.window
//    // 设置弹出位置
//    window!!.setGravity(Gravity.CENTER)
//    // 设置弹出动画
//    window.setWindowAnimations(R.style.main_menu_animStyle)
//    // 设置对话框大小(宽度为父布局宽度，高度为自适应)
//    window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//   // window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//    dialog.show()
//    return dialog
//}

/*
  打印：
 */
fun printLog(str : String){
    Log.d("logDInHaaRoh", "print: $str")
}