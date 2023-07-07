package com.gankao.common.webview

import android.os.Bundle
import android.webkit.JavascriptInterface
import androidx.fragment.app.FragmentActivity
import com.gankao.common.throwable.CustomException
import org.json.JSONObject

/**
 * @Description TODO
 * @Author sujinlin
 * @Date 04-27-2023 周四 14:02
 */
open class WebViewJavaScriptInterface(private val mContext: FragmentActivity) {
    // Add the remaining 8 methods here
    var data: String? = null
        private set

    @JavascriptInterface
    @Throws(CustomException::class)
    fun navigateToSchema(scheme: String, tokenInfo: String) {
        mContext.runOnUiThread {
            try {
                //todo 跳转到指定scheme
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    @JavascriptInterface
    @Throws(CustomException::class)
    fun navigateWebPage(content: String) {

        mContext.runOnUiThread {
            try {
                val jsonObject = JSONObject(content)
                val url = jsonObject.getString("url")

                //todo 打开一个新的web activity
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @JavascriptInterface
    @Throws(CustomException::class)
    fun goToNativePage(content: String) {
        mContext.runOnUiThread {
            try {
                val jsonObject = JSONObject(content)
                if (jsonObject.has("schema") && jsonObject.getString("schema").isNotEmpty()) {
                    if (jsonObject.getString("schema").contains("kouyu")) {
                        //todo 跳转到英语听说
                    }
                    return@runOnUiThread
                }
                if (jsonObject.has("fallUrl")) {
                    //todo 加载url
//                    webView.loadUrl(jsonObject.getString("fallUrl"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @JavascriptInterface
    @Throws(CustomException::class)
    fun openCourseVideo(content: String) {
        try {
            val bundle = Bundle()
            val jsonObject = JSONObject(content)
            if (jsonObject.has("courseID")
                && jsonObject.has("sectionID")
                && jsonObject.has("recommend")
            ) {
                bundle.putString("COURSEID", jsonObject.getString("courseID"))
                bundle.putString("SECTIONID", jsonObject.getString("sectionID"))
                bundle.putString("RECOMMEND", jsonObject.getString("recommend"))
            } else if (jsonObject.has("courseID") && jsonObject.has("sectionID")) {
                bundle.putString("COURSEID", jsonObject.getString("courseID"))
                bundle.putString("SECTIONID", jsonObject.getString("sectionID"))
            } else if (jsonObject.has("courseID") && !jsonObject.has("sectionID")) {
                bundle.putString("COURSEID", jsonObject.getString("courseID"))
            }

            //todo 跳转到视频播放页
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JavascriptInterface
    fun inputVoice(voiceJson: String) {
        //todo 语音输入
    }

    @JavascriptInterface
    fun inputFile(fileJsonObject: String) {
        //todo 文件上传
    }


    @JavascriptInterface
    fun startPay(payInfo: String) {
        //调用原生支付
    }

    @JavascriptInterface
    open fun playAudio(playTextBean:String){
        //播放音频
    }
}