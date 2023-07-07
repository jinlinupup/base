package com.gankao.app_camerax

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import java.util.concurrent.ExecutionException


class MainActivity : AppCompatActivity() {

    private var pvCamera: PreviewView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pvCamera = findViewById(R.id.pv_camera)
        requestPenPermissions()
    }


    private fun requestPenPermissions() {
        val permissions: MutableList<String> = ArrayList()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            permissions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE)
        } else {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        permissions.add(Manifest.permission.CAMERA)

        PermissionsManager.request(this, permissions, { list, all ->
            if (all){
                startCamera()
            }

        }, { list, doNotAskAgain ->

        })
    }

    /**
     * 开始预览
     */
    private fun startCamera() {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
            ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener(Runnable {
            try {
                //将相机的生命周期和activity的生命周期绑定，camerax 会自己释放
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
                val preview: Preview = Preview.Builder().build()
                //创建图片的 capture
                val mImageCapture = ImageCapture.Builder()
                    .setFlashMode(ImageCapture.FLASH_MODE_OFF)
                    .build()
                //选择前置摄像头
                val cameraSelector =
                    CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                        .build()
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                //参数中如果有mImageCapture才能拍照，否则会报下错
                //Not bound to a valid Camera [ImageCapture:androidx.camera.core.ImageCapture-bce6e930-b637-40ee-b9b9-
                val mCamera = cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    mImageCapture
                )
                preview.setSurfaceProvider(pvCamera!!.getSurfaceProvider())
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }
}
