package com.gankao.crosssdk

import android.app.Application
import android.content.Context
import android.os.Binder
import android.util.Log
import cn.jinlin.lib_statepage.MultiStateConfig
import cn.jinlin.lib_statepage.MultiStatePage
import com.android.tony.defenselib.DefenseCrash
import com.android.tony.defenselib.handler.IExceptionHandler

//import com.gankao.crosssdk.umeng.UmengHelper

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-27-2023 周四 18:28
 *
 */
class App : Application() {
    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
//        DefenseCrash.initialize(this)
//        DefenseCrash.install(this)
    }

    override fun onCreate() {

        super.onCreate()
//        throw NullPointerException("测试崩溃 Application onCreate")


        println("获取calling-Uid 上一个：" + Binder.getCallingUid())
        println("获取calling-Pid  上一个：" + Binder.getCallingPid())

        println("获取calling-UID：" + Binder.LAST_CALL_TRANSACTION)
        println("获取calling-UID：" + android.os.Process.LAST_APPLICATION_UID)

        println("获取calling-Pid 自身的：" + android.os.Process.myPid())
        println("获取calling-Uid 自身的：" + android.os.Process.myUid())

        val config = MultiStateConfig.Builder()
            .alphaDuration(300)
            .errorIcon(cn.jinlin.lib_statepage.R.mipmap.state_error)
            .emptyIcon(cn.jinlin.lib_statepage.R.mipmap.state_empty)
            .emptyMsg("当前还没有数据哦")
            .loadingMsg("加载中...")
            .errorMsg("加载失败了")
            .build()

        MultiStatePage.config(config)

        initUmeng()
    }

    private fun initUmeng() {
//        UmengHelper.preInit(this)
//        UmengHelper.init(this)
    }

    override fun onTerminate() {
        //程序结束
        println("-----------------程序结束------------------")
        super.onTerminate()


    }


//    @Throws(Throwable::class)
//    override fun onCaughtException(
//        thread: Thread,
//        throwable: Throwable,
//        isSafeMode: Boolean,
//        isCrashInChoreographer: Boolean
//    ) {
//        Log.i(
//            "Exceptionhandler",
//            "thread:${thread.name} " +
//                    "exception:${throwable.message} " +
//                    "isCrashInChoreographer:$isCrashInChoreographer " +
//                    "isSafeMode:$isSafeMode"
//        )
//        throwable.printStackTrace()
//    }


}