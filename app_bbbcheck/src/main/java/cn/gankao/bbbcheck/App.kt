package cn.gankao.bbbcheck

import android.app.Application

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 19:54
 *
 */
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}