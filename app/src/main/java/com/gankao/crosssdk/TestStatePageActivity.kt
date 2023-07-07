package com.gankao.crosssdk

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.jinlin.lib_statepage.MultiStateContainer
import cn.jinlin.lib_statepage.bindMultiState
import cn.jinlin.lib_statepage.state.EmptyState
import cn.jinlin.lib_statepage.state.ErrorState
import cn.jinlin.lib_statepage.state.SuccessState
import com.gankao.common.manager.GKPermission
import com.gankao.common.manager.PermissionsManager
import com.gankao.crosssdk.TestApi.testStart
import com.gankao.crosssdk.databinding.ActivityTestStatePageBinding
import com.gankao.crosssdk.databinding.CourseChapterRecycleItemBinding
import com.gankao.crosssdk.state.LottieOtherState
import com.gankao.network.getExceptionStr
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class TestStatePageActivity : AppCompatActivity() {

    val multiStateActivityRoot: MultiStateContainer by lazy { bindMultiState() }
    lateinit var binding: ActivityTestStatePageBinding
    private val courseChapterAdapter: CourseChapterAdapter by lazy { CourseChapterAdapter() }
    private var courseChapterList: MutableList<CourseChapter> = ArrayList()
    val multiStateView: MultiStateContainer by lazy { binding.tvTestJson.bindMultiState() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("intent解析:"+intent.toString())
        println("intent解析:"+intent.dataString)
        binding = ActivityTestStatePageBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        initCourseChapterAdapter()



        initPage()
        binding.viewCourse.setOnClickListener {
            val strings = arrayListOf(
                GKPermission.ACCESS_COARSE_LOCATION,
                GKPermission.ACCESS_FINE_LOCATION,
                GKPermission.BLUETOOTH_SCAN,
                GKPermission.BLUETOOTH_CONNECT,
                GKPermission.BLUETOOTH_ADVERTISE
            )
            PermissionsManager.request(this, strings,
                { _, _ ->

                }, { _, _ ->

                })
        }


        if (!hasUsageStatsPermission(this)){
            requestUsageStatsPermission(this)
            return
        }
//        println("当前来源callingPackage:${getReferrerPackageName(this)}")
        val callingPackage = callingPackage
        val callingActivity = callingActivity

        if (callingPackage != null) {
            println("Calling package: $callingPackage")
        }

        if (callingActivity != null) {
            val packageName = callingActivity.packageName
            val className = callingActivity.className
            println("Calling package name: $packageName")
            println("Calling class name: $className")
        }
    }

    fun requestUsageStatsPermission(activity: Activity) {
        val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
        activity.startActivity(intent)
    }

    fun hasUsageStatsPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
            val mode = appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                context.getPackageName()
            )
            return mode == AppOpsManager.MODE_ALLOWED
        }
        return false
    }

    /**
     * 通过反射获取referrer
     * @return
     */
    private fun reflectGetReferrer(): String {
        var packageName = ""
        val intent = intent
        packageName = if (intent != null && intent.component != null) {
            intent.component!!.packageName
        } else {
            val pm = packageManager
            val resolveInfo = pm.resolveActivity(
                Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER),
                0
            )
            resolveInfo!!.activityInfo.packageName
        }
        return packageName
    }

    private fun request() {
        multiStateView.showLoading()
        MainScope().launch {
            TestApi.getApi().test1().testStart({
                Log.e("initPage", "initPage: $it")
                binding.tvTestJson.text = it.toString()
                multiStateView.showSuccess()
            }, { it ->
                it.printStackTrace()

                Log.e("initPage", "error:${it.getExceptionStr()} ${it.message}")
                multiStateView.showError { errorState ->
                    errorState.retry {
                        request()
                    }
                }

            })
        }
    }

    private fun initPage() {

        for (i in 0 until 5) {
            courseChapterList.add(CourseChapter("$i", "第${i}章"))
        }
        courseChapterAdapter.submitList(courseChapterList)

    }

    //初始化rv_course_chapter的适配器，设置LinearLayoutManager
    private fun initCourseChapterAdapter() {
        binding.rvCourseChapter.layoutManager = LinearLayoutManager(this)
//        courseChapterList.observe(
//            this,
//        ) {
//            Log.e("initCourseChapterAdapter", "initCourseChapterAdapter: ${it.size}")
//            courseChapterAdapter.submitList(it)
//        }
        binding.rvCourseChapter.adapter = courseChapterAdapter

    }

    //新建rv_course_chapter的适配器，继承recycler下的ListAdapter
    inner class CourseChapterAdapter :
        ListAdapter<CourseChapter, CourseChapterAdapter.ViewHolder>(CourseChapterDiffCallback()) {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                CourseChapterRecycleItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val courseChapter = getItem(position)
            holder.apply {
                bind(courseChapter)
                itemView.tag = courseChapter
            }
        }

        inner class ViewHolder(
            private val bd: CourseChapterRecycleItemBinding
        ) : RecyclerView.ViewHolder(bd.root) {

            fun bind(item: CourseChapter) {
                bd.apply {
                    tvCourseChapterName.text = item.name
                    tvCourseChapterName.setOnClickListener {
                        Log.e("ViewHolder", "bind: ${item.name}")
                        multiStateView.show<LottieOtherState>()
                        MainScope().launch {
                            delay(1000)
                            when (item.id) {
                                "0" -> multiStateView.show<SuccessState>()
                                "1" -> multiStateView.show<ErrorState> {
                                    it.retry {
                                        multiStateView.show<LottieOtherState>()
                                        MainScope().launch {
                                            delay(1000)
                                            multiStateView.show<SuccessState>()
                                        }
                                    }
                                }
                                "2" -> multiStateView.show<EmptyState>()
                                else -> multiStateView.show<SuccessState>()
                            }

                        }
                    }
                }
            }
        }


    }

    //新建rv_course_chapter的DiffCallback，继承recycler下的DiffUtil.ItemCallback
    class CourseChapterDiffCallback : DiffUtil.ItemCallback<CourseChapter>() {
        override fun areItemsTheSame(oldItem: CourseChapter, newItem: CourseChapter): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CourseChapter, newItem: CourseChapter): Boolean {
            return oldItem == newItem
        }
    }

    //新建CourseChapter类
    data class CourseChapter(
        val id: String,
        val name: String,
    )

    fun getReferrerPackageName(activity: Activity): String? {
        var referrerPackageName: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val usageStatsManager =
                activity.getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
            val currentTime = System.currentTimeMillis()

            // 获取最近一段时间内所有应用的使用情况
            val queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                currentTime - 1000 * 60 * 60,
                currentTime
            )
            if (queryUsageStats != null) {
                var recentApp: UsageStats? = null
                for (usageStats in queryUsageStats) {
                    if (recentApp == null || recentApp.lastTimeUsed < usageStats.lastTimeUsed) {
                        recentApp = usageStats
                    }
                }

                // 获取前一个应用的包名
                if (recentApp != null) {
                    referrerPackageName = recentApp.packageName
                }
            }
        }
        return referrerPackageName
    }
}