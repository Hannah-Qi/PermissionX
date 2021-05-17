package com.permissionx.hannahqidev

import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

/*
* Google在Fragment中也提供了一份相同的API，使得我们在Fragment中也能申请运行时的权限
*
* 在这个隐藏的Fragment中对运行时权限的API进行封装
* */
typealias PermissionCallback = (Boolean,List<String>) -> Unit //typealias可以给任意类型指定一个别名
class InvisibleFragment : Fragment(){
    //callback变量作为运行时权限申请结果的回调通知方式，并将它声明成了一种函数类型变量，该函数类型接收Boolean和List<String>)且没有返回值
    private var callback: PermissionCallback? = null

    //外部调用方自主指定要申请哪些权限的功能
    fun requestNow(cb: PermissionCallback,vararg permissions: String){
        callback = cb
        requestPermissions(permissions,1)
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray) {
        if(requestCode == 1){
            //deniedList列表记录所有被用户拒绝的权限
            val deniedList = ArrayList<String>()
            //遍历grantResults数组，如果发现某个权限未被用户授权，就将它添加到deniedList中
            for((index, result) in grantResults.withIndex()){
                if(result != PackageManager.PERMISSION_GRANTED){
                    deniedList.add(permissions[index])
                }
            }
            //判断所有申请的权限是否均已被授权
            val allGranted = deniedList.isEmpty()
            //callback变量对运行时权限的申请结果进行回调
            callback?.let {
                it(allGranted,deniedList)
            }
        }
    }
}