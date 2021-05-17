package com.permissionx.hannahqidev

import androidx.fragment.app.FragmentActivity

object PermissionX {
    private const val TAG = "InvisibleFragment"
    //FragmentActivity是AppCompatActivity的父类
    fun request(activity: FragmentActivity,vararg permissions: String,callback: PermissionCallback){
        val fragmentManager = activity.supportFragmentManager

        //判断传入的Activity参数中是否已经包含了指定TAG的Fragment
        val existedFragment = fragmentManager.findFragmentByTag(TAG)
        //如果已经包含直接使用该Fragment，否则就创建一个新的InvisibleFragment实例，
        //并将它添加到Activity中，同时指定一个TAG
        val fragment = if(existedFragment != null){
            existedFragment as InvisibleFragment
        }else{
            val invisibleFragment = InvisibleFragment()
            fragmentManager.beginTransaction().add(invisibleFragment, TAG).commitNow()//结束时就要调用commitNow()，不能调用commit()
            invisibleFragment
        }
        fragment.requestNow(callback, *permissions)//* 不是指针的意思，而是表示将一个数组转换成可变长度参数传递过去
    }
}