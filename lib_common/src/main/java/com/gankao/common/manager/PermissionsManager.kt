package com.gankao.common.manager

import android.content.Context
import androidx.annotation.NonNull
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions

/**
 * @Description 权限管理类，针对项目中使用的第三方权限库或者系统自带的权限请求方式进行封装，便于维护和替换
 * @Author sujinlin
 * @Date 05-31-2023 周三 10:32
 *
 */
object PermissionsManager {

    /**
     * 权限申请
     *
     * @param context
     * @param permissions
     * @param onGranted
     * @param onDenied
     */
    fun request(
        context: Context,
        permissions: List<String>,
        onGranted: (permissions: List<String>, allGranted: Boolean) -> Unit,
        onDenied: (permissions: List<String>, doNotAskAgain: Boolean) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(permissions)
            .request(object : OnPermissionCallback {
                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    onDenied(permissions, doNotAskAgain)
                }

                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    onGranted(permissions, allGranted)
                }
            })
    }
}