package cn.gankao.bbbcheck

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import cn.gankao.bbbcheck.api.TestApi
import cn.gankao.bbbcheck.api.TestApi.testStart
import cn.gankao.bbbcheck.databinding.ActivityBbbdrawBinding
import cn.gankao.bbbcheck.draw.PageInfo
import cn.gankao.bbbcheck.utils.EventBusUtils
import cn.gankao.bbbcheck.utils.MathUtil
import cn.gankao.bbbcheck.utils.SizeUtil
import com.bbb.bpen.model.PointData
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.math.BigDecimal

class BBBDrawActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBbbdrawBinding
    private var curPageId = 0L
    private var isRequest = false
    private var caches: MutableList<PointData> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBbbdrawBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)


        EventBusUtils.register(this)

    }

    private fun request(pointData: PointData) {
        if (binding.tvTips.isVisible) {
            binding.tvTips.isVisible = false
        }
        val REQUEST_PAGEINFO =
            "query my_getAipenPaperPageInfo(\$inputPaperInfo:InputPaperInfo,\$pageKey:String){my_getAipenPaperPageInfo(inputPaperInfo:\$inputPaperInfo,pageKey:\$pageKey)}"
        val map = HashMap<String, Any>()
        val variables = HashMap<String, Any>()
        val imageMap = HashMap<String, Any>()
        imageMap["pageID"] = ""+pointData.page_id
        imageMap["sizeType"] = ""+pointData.paper_type
        imageMap["pageType"] = "BBB"
        variables["inputPaperInfo"] = imageMap
        map["query"] = REQUEST_PAGEINFO
        map["variables"] = variables

        MainScope().launch {
            TestApi.getApi().getPenData(map,"9571149,c6938574b50e94ddb32dab3fbc79e9ff,nihao").testStart({
                it?.apply {
                    settingData(this.my_getAipenPaperPageInfo)
                }
            }, { it ->
                it.printStackTrace()
                ToastUtils.showShort("页面加载失败，请联系提供方")
                curPageId = 0L
                isRequest = false
            })
        }
    }

    private var scale: Float = 0f
    private fun settingData(pageInfo: PageInfo.MyGetPagePointInfoBean) {
        val realWidth = SizeUtil.getScreenWidth(this)
        val bHeight = pageInfo.page.mm_height
        val bWidth = pageInfo.page.mm_width

        scale = MathUtil.div(bWidth * 1f, realWidth * 1f, 4)

        val mHeight = bHeight.div(scale)

        val layoutParams = binding.imageBg.layoutParams
        layoutParams.width = realWidth
        layoutParams.height = mHeight.toInt()

        binding.imageBg.layoutParams = layoutParams

        binding.imageBg.scaleType = ImageView.ScaleType.FIT_START

        val layoutDrawParams = binding.drawOnly.layoutParams
        layoutDrawParams.width = realWidth
        layoutDrawParams.height = mHeight.toInt()
        binding.drawOnly.layoutParams = layoutDrawParams

        binding.drawOnly.clearAll()
        binding.drawOnly.Init(realWidth, mHeight.toInt())

        Glide.with(this)
            .load(pageInfo.page.image_url)
            .into(object : ImageViewTarget<Drawable>(binding.imageBg) {
                override fun setResource(resource: Drawable?) {

                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                }

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    super.onResourceReady(resource, transition)
                    binding.imageBg.setImageDrawable(resource)
                }

            })


        if (caches.size > 0) {
            caches.forEach { point ->
                drawPoint(point)
            }
            caches.clear()
        }
        isRequest = false
    }

    private var index = 0
    private fun drawPoint(point: PointData) {
        if (point._x == 0f || point._y == 0f || point.isStroke_end){
            return
        }
        val fx = point._x.div(scale)
        val fy = point._y.div(scale)
        index++
        if (point.isStroke_start){
            index = 0
        }

        binding.drawOnly.drawBmpPoint(fx, fy, index)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun getEvent(point: Event.PointBean) {
        if (curPageId == 0L || curPageId != point.bean.page_id) {
            curPageId = point.bean.page_id
            isRequest = true
            caches.add(point.bean)
            request(point.bean)
            return
        }
        if (isRequest) {
            caches.add(point.bean)
            return
        }

        drawPoint(point.bean)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtils.unRegister(this)
    }
}