package com.gankao.crosssdk

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.core.impl.utils.executor.CameraXExecutors
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.ToastUtils
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutionException


class MainActivity : AppCompatActivity() {

    private var pvCamera: PreviewView? = null
    private lateinit var btnRecord: Button
    private lateinit var btnCheckType: Button
    private lateinit var btnPhoto: Button
    private lateinit var btnCheck: Button

    private var mCamera: Camera? = null
    private var preview: Preview? = null
    private var mImageCapture: ImageCapture? = null
    private var mVideoCapture: VideoCapture? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private var isBackCamera = false
    private var isRecord = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pvCamera = findViewById(R.id.pv_camera)
        btnRecord = findViewById(R.id.btnRecord)
        btnCheckType = findViewById(R.id.btnCheckType)
        btnPhoto = findViewById(R.id.btnPhoto)
        btnCheck = findViewById(R.id.btnCheck)
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
            if (all) {
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
                cameraProvider = cameraProviderFuture.get()
                preview = Preview.Builder().build()
                //创建图片的 capture
                mImageCapture = ImageCapture.Builder()
                    .setFlashMode(ImageCapture.FLASH_MODE_OFF)
                    .build()
                //选择前置摄像头
                val cameraSelector =
                    CameraSelector.Builder()
                        .requireLensFacing(if (isBackCamera) CameraSelector.LENS_FACING_BACK else CameraSelector.LENS_FACING_FRONT)

                        .build()
                // Unbind use cases before rebinding
                cameraProvider!!.unbindAll()

                // Bind use cases to camera
                //参数中如果有mImageCapture才能拍照，否则会报下错
                //Not bound to a valid Camera [ImageCapture:androidx.camera.core.ImageCapture-bce6e930-b637-40ee-b9b9-
                mCamera = cameraProvider!!.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    mImageCapture
                )
                preview?.setSurfaceProvider(pvCamera!!.getSurfaceProvider())
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    fun startPhoto(view: View?) {
        if (mImageCapture != null) {
            val file = createImgFile()

            //创建包文件的数据，比如创建文件
            val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()
            mImageCapture?.takePicture(
                outputFileOptions,
                ContextCompat.getMainExecutor(this),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        ToastUtils.showLong("图片保存成功:保存位置: " + outputFileResults.savedUri)
                        // 保存成功
                        // 将图片保存到MediaStore中
                        val contentValues = ContentValues()
                        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, file.getName())
                        contentValues.put(MediaStore.Images.Media.MIME_TYPE,  "image/jpeg")
                        contentValues.put(
                            MediaStore.Images.Media.RELATIVE_PATH,
                            Environment.DIRECTORY_PICTURES + "/CameraX"
                        )
                        contentResolver.insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            contentValues
                        )

                        sendBroadcast(
                            Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(
                                    "file://${file.absolutePath}"
                                )
                            )
                        )

                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.e("TAG", "onError: ${exception.message}")
                    }

                })
        }
    }

    fun checkType(view: View?) {
        isRecord = !isRecord
        if (isRecord) {
            btnCheckType.setText("当前为录屏模式")
            btnPhoto.visibility = View.GONE
            btnRecord.visibility = View.VISIBLE
            initVideo()
        } else {
            btnCheckType.setText("当前为拍照模式")
            btnRecord.visibility = View.GONE
            btnPhoto.visibility = View.VISIBLE
            initPhoto()
        }
    }

    private fun initPhoto() {
        mImageCapture = ImageCapture.Builder()
            .setFlashMode(ImageCapture.FLASH_MODE_OFF)
            .build()
        //选择前置摄像头
        val cameraSelector =
            CameraSelector.Builder()
                .requireLensFacing(if (isBackCamera) CameraSelector.LENS_FACING_BACK else CameraSelector.LENS_FACING_FRONT)
                .build()
        // Unbind use cases before rebinding
        cameraProvider!!.unbindAll()
        mCamera = cameraProvider!!.bindToLifecycle(
            this,
            cameraSelector,
            preview,
            mImageCapture
        )
        preview?.setSurfaceProvider(pvCamera!!.getSurfaceProvider())
    }

    private fun initVideo() {
        //创建图片的 capture
//        mImageCapture = ImageCapture.Builder()
//            .setFlashMode(ImageCapture.FLASH_MODE_OFF)
//            .build();
        mVideoCapture = VideoCapture.Builder().build()

        //选择前置摄像头
        val cameraSelector =
            CameraSelector.Builder()
                .requireLensFacing(if (isBackCamera) CameraSelector.LENS_FACING_BACK else CameraSelector.LENS_FACING_FRONT)

                .build()
        // Unbind use cases before rebinding
        cameraProvider!!.unbindAll()

        // Bind use cases to camera
        //参数中如果有mImageCapture才能拍照，否则会报下错
        //Not bound to a valid Camera [ImageCapture:androidx.camera.core.ImageCapture-bce6e930-b637-40ee-b9b9-
        mCamera = cameraProvider!!.bindToLifecycle(
            this,
            cameraSelector,
            preview,
            mVideoCapture
        )
        preview?.setSurfaceProvider(pvCamera!!.getSurfaceProvider())
    }

    //btnCheck
    fun checkCamera(view: View?) {
        isBackCamera = !isBackCamera
        cameraProvider?.let {
            cameraProvider?.unbindAll()
            val cameraSelector =
                CameraSelector.Builder()
                    .requireLensFacing(if (isBackCamera) CameraSelector.LENS_FACING_BACK else CameraSelector.LENS_FACING_FRONT)
                    .build()
            cameraProvider?.bindToLifecycle(
                this,
                cameraSelector,
                preview,
                if (isRecord) mVideoCapture else mImageCapture
            )
        }
    }

    fun startVideo(view: View?) {
        if (TextUtils.equals("开始录制", btnRecord.getText().toString())) {
            btnCheck.visibility = View.GONE
            isRecord = true
            btnRecord.setText("停止录制")
            startRecord()
        } else {
            btnRecord.setText("开始录制")
            btnCheck.visibility = View.VISIBLE
            stopRecord()
        }
    }

    @SuppressLint("MissingPermission", "RestrictedApi")
    private fun startRecord() {
        if (mVideoCapture != null) {
            val file = createVideoFile()

            val build: VideoCapture.OutputFileOptions =
                VideoCapture.OutputFileOptions.Builder(file).build()
            mVideoCapture!!.startRecording(
                build,
                CameraXExecutors.mainThreadExecutor(),
                object : VideoCapture.OnVideoSavedCallback {
                    override fun onVideoSaved(@NonNull outputFileResults: VideoCapture.OutputFileResults) {

                        ToastUtils.showLong("视频保存成功:保存位置: " + outputFileResults.savedUri)
                        // 视频保存成功
                        // 将视频保存到MediaStore中
                        val contentValues = ContentValues()
                        contentValues.put(MediaStore.Video.Media.DISPLAY_NAME, file.getName())
                        contentValues.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                        contentValues.put(
                            MediaStore.Video.Media.RELATIVE_PATH,
                            Environment.DIRECTORY_MOVIES + "/CameraX"
                        )
                        contentResolver.insert(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            contentValues
                        )

                        sendBroadcast(
                            Intent(
                                Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(
                                    "file://${file.absolutePath}"
                                )
                            )
                        )

                    }

                    override fun onError(
                        videoCaptureError: Int,
                        @NonNull message: String,
                        @Nullable cause: Throwable?
                    ) {
                        Log.e("TAG", "onError: $message")
                    }
                })
        }
    }

    @SuppressLint("RestrictedApi")
    private fun stopRecord() {
        mVideoCapture!!.stopRecording()
    }

    // 创建保存视频的文件
    private fun createVideoFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(
            System.currentTimeMillis()
        )
        val fileName = "VIDEO_$timestamp.mp4"

        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        return File(storageDir, fileName)
    }

    // 创建保存图片的文件
    private fun createImgFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(
            System.currentTimeMillis()
        )
        val fileName = "img_$timestamp.jpg"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            var rootPath: String? = ""
            rootPath = if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {//有SD卡
                Environment.getExternalStorageDirectory().absolutePath + "/";
            } else {
                Environment.getDataDirectory().absolutePath + "/";
            }

            val file = File(rootPath + "CameraX")
            if (!file.exists()) {
                file.mkdir()
            }
            return File(file, fileName)
        }
        val storageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + "/CameraX")
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        return File(storageDir, fileName)
    }
}
