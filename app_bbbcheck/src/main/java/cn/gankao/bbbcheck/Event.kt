package cn.gankao.bbbcheck

import android.bluetooth.BluetoothDevice
import com.bbb.bpen.model.PointData

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 19:53
 *
 */
class Event {
    class SendDevices(var device: BluetoothDevice)
    class PointBean(var bean: PointData)
    class ConnectSuccess()
}