package cn.gankao.bbbcheck

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import cn.gankao.bbbcheck.adapter.PenSearchAdapter
import cn.gankao.bbbcheck.databinding.ActivityBbbhomeBinding
import cn.gankao.bbbcheck.utils.EventBusUtils
import com.bbb.bpen.command.BiBiCommand
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BBBHomeActivity : AppCompatActivity() {
    lateinit var binding: ActivityBbbhomeBinding

    private val penSearchAdapter: PenSearchAdapter by lazy { PenSearchAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBbbhomeBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)

        EventBusUtils.register(this)

        BBBServiceManager.bindBBBService()

        initAdapter()
        binding.tvSearch.setOnClickListener {
            requestPermissions()
        }

        binding.tvJumpDraw.setOnClickListener {
            startActivity(Intent(this, BBBDrawActivity::class.java))
        }
    }

    private fun initAdapter() {
        binding.rvPenList.layoutManager = LinearLayoutManager(this)
        penSearchAdapter.setOnItemClickListener { device, pos ->
            BiBiCommand.stopscan(this.application)
            BiBiCommand.connect(this.application, device.address)
        }

        binding.rvPenList.adapter = penSearchAdapter


        //dv_draw_only
    }

    private fun requestPermissions() {
        val mPermissionList: MutableList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            // Android 版本大于等于 Android12 时
            // 只包括蓝牙这部分的权限，其余的需要什么权限自己添加
            mPermissionList.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
            mPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            mPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            mPermissionList.add(Manifest.permission.BLUETOOTH_SCAN)
            mPermissionList.add(Manifest.permission.BLUETOOTH_ADVERTISE)
            mPermissionList.add(Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            // Android 版本小于 Android12 及以下版本
            mPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            mPermissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        XXPermissions.with(this)
            .permission(mPermissionList)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, all: Boolean) {
                    if (all) {
                        if (!checkBluetoothAndGPS()) {
                            return
                        }
                        BiBiCommand.startScanWithQueue(this@BBBHomeActivity.application)
                    } else {
                        Toast.makeText(
                            this@BBBHomeActivity,
                            "请打开位置权限、蓝牙权限，才能搜索到智能笔哦",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }

                override fun onDenied(permissions: MutableList<String>, never: Boolean) {
                    Toast.makeText(
                        this@BBBHomeActivity,
                        "请打开位置权限、蓝牙权限，才能搜索到智能笔哦",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }


    private fun checkBluetoothAndGPS(): Boolean {
        if (!isGpsEnable()) {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            Toast.makeText(
                this@BBBHomeActivity,
                "请打开位置信息，才能扫描到附近的蓝牙",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }

        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (adapter == null || !adapter.isEnabled) {
            adapter!!.enable()
            return false
        }
        return true
    }

    private fun isGpsEnable(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return gps || network
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getEvent(sendDevices: Event.SendDevices) {

        penSearchAdapter.addDevice(sendDevices.device)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getEvent(sendDevices: Event.ConnectSuccess) {
        startActivity(Intent(this, BBBDrawActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        BBBServiceManager.unbindBBBService()
        EventBusUtils.unRegister(this)
    }
}