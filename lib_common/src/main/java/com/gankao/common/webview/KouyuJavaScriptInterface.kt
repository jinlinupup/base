package com.gankao.common.webview

import android.media.MediaPlayer.OnCompletionListener
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity
import com.constraint.CoreProvideTypeEnum
import com.constraint.OffLineSourceEnum
import com.constraint.ResultBody
import com.gankao.common.ApiConfig
import com.gankao.common.kouyu.AudioRecoderUtils
import com.gankao.common.kouyu.KouyuEndBean
import com.gankao.common.kouyu.KouyuInitBean
import com.gankao.common.throwable.CustomException
import com.gankao.network.LogUtil
import com.google.gson.Gson
import com.xs.SingEngine
import com.xs.impl.ResultListener
import org.json.JSONObject
import kotlin.concurrent.thread

/**
 * @Description 口语测评h5 封装
 * @Author sujinlin
 * @Date 04-27-2023 周四 15:16
 *
 */
open class KouyuJavaScriptInterface(
    private val mContext: FragmentActivity,
    private val webView: WebView,
) : WebViewJavaScriptInterface(mContext), ResultListener {

    private var mRecoderUtils: AudioRecoderUtils? = null

    private var mSingEngine: SingEngine? = null
    private var kouyuInitBean: KouyuInitBean? = null

    private var playAudioSuccess: String? = null
    private var playBackSuccess: String? = null
    private var kouyuOnError: String? = null
    private var kouyuonResult: String? = null
    private var onUpdateVolume: String? = null
    private var onBackVadTimeOut: String? = null
    private var onFrontVadTimeOut: String? = null

    private var onCompletionListener = OnCompletionListener {
        mContext.runOnUiThread {
            webView.evaluateJavascript(
                "try{javascript:$playAudioSuccess}catch(e){}"
            ) { value -> LogUtil.e(value) }
        }
    }

    @JavascriptInterface
    fun kouyuEvaluatingInitializeEngine(kouyuInitStr: String) {

        mRecoderUtils = AudioRecoderUtils()
        kouyuInitBean = Gson().fromJson(kouyuInitStr, KouyuInitBean::class.java)
        if (mSingEngine == null) {
            initEngine()
        }
    }

    /**
     * 口语测评开始
     *
     * @param kouyuStart
     */
    @JavascriptInterface
    fun kouyuEvaluatingStart(kouyuStart: String) {
        if (mSingEngine == null) {
            mRecoderUtils = AudioRecoderUtils()
            initEngine()
        }

        thread {
            try {
                val jsonObject = JSONObject(kouyuStart)
                kouyuOnError = jsonObject.getString("onError")
                onUpdateVolume = jsonObject.getString("onUpdateVolume")
                onBackVadTimeOut = jsonObject.getString("onBackVadTimeOut")
                onFrontVadTimeOut = jsonObject.getString("onFrontVadTimeOut")
                kouyuonResult = jsonObject.getString("onResult")
                if (jsonObject.has("request")) {
                    startpingce(jsonObject.getJSONObject("request"))
                }
            } catch (ex: CustomException) {
                ex.printStackTrace()
            }
        }.start()
    }


    /**
     * 口语测评结束
     *
     * @param kouyuend
     */
    @JavascriptInterface
    fun kouyuEvaluatingEnd(kouyuend: String) {
        thread {
            try {
                mRecoderUtils!!.stopRecord()
                val jsonObject = JSONObject(kouyuend)
                kouyuOnError = jsonObject.getString("onError")
                pingCeStop()
            } catch (ex: CustomException) {
                ex.printStackTrace()
            }
        }.start()
    }

    /**
     * 播放最后一次录音
     *
     * @param playbackBean
     */
    @JavascriptInterface
    fun kouyuEvaluatingPlayBack(playbackBean: String) {
        mSingEngine?.playback()
    }


    /**
     * 播放指定文本
     *
     * @param playTextBean
     */
    @JavascriptInterface
    @Throws(CustomException::class)
    override fun playAudio(playTextBean: String) {
        val jsonObject = JSONObject(playTextBean)
        playAudioSuccess = jsonObject.getString("onSuccess")
        //todo mediaPlayer播放音频

    }

    /**
     * 暂停回放
     *
     * @param playStopBean
     */
    @JavascriptInterface
    fun voidkouyuEvaluatingPlayStop(playStopBean: String) {
        mSingEngine?.stopPlayBack()
    }

    /**
     * 设置是否开启VAD功能
     *
     * @param setOpenVadStr
     */
    @JavascriptInterface
    @Throws(CustomException::class)
    fun kouyuEvaluatingSetOpenVad(setOpenVadStr: String) {
        val jsonObject = JSONObject(setOpenVadStr)
        val isOpenVad = jsonObject.getBoolean("vad")
        if (isOpenVad) {
            mSingEngine?.setOpenVad(true, "vad.0.1.bin")
        } else {
            mSingEngine?.setOpenVad(false, null)
        }
    }

    /**
     * 设置前置 vad 时间，时间超时后会自动取消测评，不返回结果
     *
     * @param setFrontVadTimeStr
     */
    @JavascriptInterface
    @Throws(CustomException::class)
    fun kouyuEvaluatingSetFrontVadTime(setFrontVadTimeStr: String) {
        val jsonObject = JSONObject(setFrontVadTimeStr)
        val frontVadTime = jsonObject.getLong("frontVadTime")
        mSingEngine?.setFrontVadTime(frontVadTime)
    }

    /**
     * 设置后置 vad 时间，时间超时后会自动取消测评，不返回结果
     *
     * @param setFrontBackTimeStr
     */
    @JavascriptInterface
    @Throws(CustomException::class)
    fun kouyuEvaluatingSetBackVadTime(setFrontBackTimeStr: String) {
        val jsonObject = JSONObject(setFrontBackTimeStr)
        val setBackVadTime = jsonObject.getLong("setBackVadTime")
        mSingEngine?.setBackVadTime(setBackVadTime)
    }

    /**
     * 取消引擎，用于出现异常情况下，用户可以取消引擎
     *
     * @param cancelQuie
     */
    @JavascriptInterface
    fun kouyuEvaluatingCancelQuiet(cancelQuie: String) {
        mSingEngine?.cancel()
    }


    /**
     * 设置在线服务器地址
     *
     * @param setServerApiStr
     */
    @JavascriptInterface
    @Throws(CustomException::class)
    fun kouyuEvaluatingSetServerAPI(setServerApiStr: String) {
        val jsonObject = JSONObject(setServerApiStr)
        val serverAPI = jsonObject.getString("serverAPI")
        mSingEngine?.setServerAPI(serverAPI)
    }

    /**
     * 设置在线服务器地址
     *
     * @param setServerApiStr
     */
    @JavascriptInterface
    @Throws(CustomException::class)
    fun kouyuEvaluatingSetTestServerAPI(setServerTestApiStr: String) {
        val jsonObject = JSONObject(setServerTestApiStr)
        val testServerAPI = jsonObject.getString("testServerAPI")
        mSingEngine?.setTestServerAPI(testServerAPI)
    }

    /**
     * 初始化口语先声引擎
     *
     */
    private fun initEngine() {
        thread {
            try {
                mSingEngine = SingEngine.newInstance(mContext)
                mSingEngine?.apply {
                    setListener(this@KouyuJavaScriptInterface)
                    setServerType(CoreProvideTypeEnum.CLOUD)
                    //todo 设置wavPath
                    wavPath = ""
                    //  是否开启vad功能
                    if (kouyuInitBean != null && kouyuInitBean!!.params != null) {
                        if (kouyuInitBean!!.params.isOpenVad) {
                            setOpenVad(true, "vad.0.1.bin")
                        } else {
                            setOpenVad(false, null)
                        }
                        if (kouyuInitBean!!.params.serverAPI != null && !kouyuInitBean!!.params.serverAPI.equals(
                                ""
                            )
                        ) {
                            setServerAPI(kouyuInitBean!!.params.serverAPI)
                        }
                        if (kouyuInitBean!!.params.testServerAPI != null && !kouyuInitBean!!.params.testServerAPI.equals(
                                ""
                            )
                        ) {
                            setTestServerAPI(kouyuInitBean!!.params.testServerAPI)
                        }
                        if (kouyuInitBean!!.params.frontVadTime > 0) {
                            setFrontVadTime(kouyuInitBean!!.params.frontVadTime)
                        }
                        if (kouyuInitBean!!.params.backVadTime > 0) {
                            setBackVadTime(kouyuInitBean!!.params.frontVadTime)
                        }
                        if (kouyuInitBean!!.params.serverTimeout > 0) {
                            setServerTimeout(kouyuInitBean!!.params.serverTimeout.toLong())
                        }
                        if (kouyuInitBean!!.params.enableVolume) {
                            enableVolume()
                        }
                    }

                    setOffLineSource(OffLineSourceEnum.SOURCE_BOTN)

                    val cfgInit = buildInitJson(ApiConfig.APPKEY, ApiConfig.SECERTKEY)
                    setNewCfg(cfgInit)
                    createEngine("deprecatedCreateEngine")
                }
            } catch (e: CustomException) {
                e.printStackTrace()
            }
        }.start()
    }

    /**
     * 口语开始录音
     */
    private fun startpingce(obj: JSONObject) {
        try {
            // 构建评测请求参数
            mSingEngine?.apply {
                val startCfg = buildStartJson("UUID", obj)
                // 设置评测请求参数
                setStartCfg(startCfg)
                // 开始测评
                start()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 停止录音
     */
    private fun pingCeStop() {
        mSingEngine?.stop()
    }

    //测评相关回调

    override fun onBegin() {

    }

    override fun onResult(result: JSONObject?) {
        mContext.runOnUiThread {
            webView.evaluateJavascript(
                StringWebUtils.wrapJs("javascript:" + kouyuonResult + "(" + result.toString() + ")")
            ) { value -> LogUtil.e(value) }
        }
    }

    override fun onEnd(resultBody: ResultBody?) {
        if (resultBody != null) {
            val kouyuEndBean = KouyuEndBean()
            kouyuEndBean.errorCode = resultBody.code
            kouyuEndBean.errorMsg = resultBody.message
            mContext.runOnUiThread {
                webView.evaluateJavascript(
                    StringWebUtils.wrapJs(
                        "javascript:$kouyuOnError(" + Gson().toJson(
                            kouyuEndBean
                        ) + ")"
                    )
                ) { value -> LogUtil.e(value) }
            }
        }
    }

    override fun onUpdateVolume(volume: Int) {
        mContext.runOnUiThread {
            webView.evaluateJavascript(
                StringWebUtils.wrapJs("javascript:$onUpdateVolume($volume)")
            ) { value -> LogUtil.e(value) }
        }
    }

    override fun onFrontVadTimeOut() {
        mContext.runOnUiThread {
            webView.evaluateJavascript(
                StringWebUtils.wrapJs("javascript:$onFrontVadTimeOut(' ')")
            ) { value -> LogUtil.e(value) }
        }
    }

    override fun onBackVadTimeOut() {
        mContext.runOnUiThread {
            webView.evaluateJavascript(
                StringWebUtils.wrapJs("javascript:$onBackVadTimeOut(' ')")
            ) { value -> LogUtil.e(value) }
        }
    }

    override fun onRecordingBuffer(data: ByteArray?, size: Int) {

    }

    override fun onRecordLengthOut() {

    }

    override fun onReady() {

    }

    override fun onPlayCompeleted() {
        mContext.runOnUiThread {
            webView.evaluateJavascript(
                StringWebUtils.wrapJs("javascript:$playBackSuccess")
            ) { value -> LogUtil.e(value) }
        }
    }

    override fun onRecordStop() {

    }


}