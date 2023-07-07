package cn.gankao.bbbcheck

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.os.Build
import android.util.Log
import cn.gankao.bbbcheck.utils.EventBusUtils
import com.bbb.bpen.command.BiBiCommand
import com.bbb.bpen.delegate.BlueDelegate
import com.bbb.bpen.model.PointData
import com.gankao.network.LogUtil
import java.text.SimpleDateFormat
import java.util.*

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 06-03-2023 周六 19:49
 *
 */
object BBBPenHelper {
    val blueDelegate = object : BlueDelegate {
        /// 扫描到智能笔
        /// @param model 笔信息
        override fun didDiscoverWithPen(p0: BluetoothDevice?, p1: Int) {

            p0?.run {
                LogUtil.e("======didDiscoverWithPen")
                EventBusUtils.postEvent(Event.SendDevices(this))

            }
        }

        /// 连接失败回调
        /// @param error 失败信息
        /// @param status 返回连接及断开的原因 参考 android.bluetooth.BluetoothGatt
        /// @param newState 返回最新的连接状态 0断开，1连接中，2连接成功，3断开中
        ///参考android.bluetooth.BluetoothProfile
        override fun didConnectFail(p0: BluetoothGatt?, p1: Int, p2: Int) {

        }
        /// 连接上蓝牙回调
        /// @param status 返回连接及断开的原因 参考 android.bluetooth.BluetoothGatt
        /// @param newState 返回最新的连接状态 0断开，1连接中，2连接成功，3断开中
        ///参考android.bluetooth.BluetoothProfile
        ///

        @SuppressLint("MissingPermission")
        override fun didConnect(p0: BluetoothDevice?, p1: Int, p2: Int) {
            //ToastUtil.show("智能笔连接成功！")

            EventBusUtils.postEvent(Event.ConnectSuccess())
        }

        /// 断开连接回调
        ///@param status 返回连接及断开的原因 参考 android.bluetooth.BluetoothGatt
        /// @param newState 返回最新的连接状态 0断开，1连接中，2连接成功，3断开中
        override fun didDisconnect(p0: BluetoothDevice?, p1: Int, p2: Int) {

        }

        /// 智能笔剩余电量（每5s获得一次）
        override fun notifyBattery(p0: Int) {

        }

        /// 实时坐标点数组
        override fun notifyRealTimePointData(p0: MutableList<PointData>?) {
            p0?.forEach {
                Log.d(
                    "BBBPenHelper",
                    "Old  X:${it._x}  Y:${it._y}  " +
                            "pageID:${it.page_id}  " +
                            "end:${it.isStroke_end} " +
                            " start:${it.isStroke_start}"
                )

                EventBusUtils.postEvent(Event.PointBean(it))
            }
        }

        /// 同步非实时数据
        override fun notifyBatchPointData(p0: MutableList<PointData>?) {

        }

        /// 智能笔固件有新版本
        override fun notifyFirmwareWithNewVersion(p0: String?) {
        }

        override fun unsynchronizedDataWithPercentage(p0: Float) {

        }

        /// 智能笔报告坐标点数据同步模式
        override fun notifyDataSynchronizationMode(p0: Int) {

        }

        /// 可以继续使用
        override fun notifyContinueToUseSuccess() {

        }

        /// 无法继续使用
        override fun notifyContinueToUseFail() {

        }

        /// 智能笔型号
        override fun notifyModel(p0: String?) {

        }

        /// 同步完成状态，智能笔固件版本需要508以上
        override fun notifySyncComplete() {

        }

        /// @param mobile 返回绑定的手机尾号，
        // 目前sdk不具有绑定和解绑手机号功能，所以连接后会掉到notifyBoundMobile需要断开笔的连接
        override fun notifyBoundMobile(p0: String?) {

        }

    }
}