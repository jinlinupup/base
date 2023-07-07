package cn.gankao.bbbcheck

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.bbb.bpen.binder.BiBiBinder
import com.bbb.bpen.service.BluetoothLEService

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-01-2023 周四 18:18
 *
 */
object BBBServiceManager {

    //BBB服务
    private var service: BluetoothLEService? = null
    private var isBound = false
    private val conn = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            isBound = true
            val bbbBinder = iBinder as BiBiBinder
            service = bbbBinder.service
            service?.setblueDelegate(BBBPenHelper.blueDelegate)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

    }

    fun bindBBBService() {
        if (isBound) {
            return
        }
        val intent = Intent(App.instance.applicationContext, BluetoothLEService::class.java)
        App.instance.applicationContext.bindService(intent, conn, Context.BIND_AUTO_CREATE)
    }

    fun unbindBBBService() {
        if (isBound) {
            App.instance.applicationContext.unbindService(conn)
            isBound = false
        }
    }

}