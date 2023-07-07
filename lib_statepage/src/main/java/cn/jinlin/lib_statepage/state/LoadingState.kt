package cn.jinlin.lib_statepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cn.jinlin.lib_statepage.MultiState
import cn.jinlin.lib_statepage.MultiStateContainer
import cn.jinlin.lib_statepage.MultiStatePage
import cn.jinlin.lib_statepage.R

/**
 * @ProjectName: MultiStatePage
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2020/9/17 14:15
 */
class LoadingState : MultiState() {
    private lateinit var tvLoadingMsg: TextView
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return inflater.inflate(R.layout.mult_state_loading, container, false)
    }

    override fun onMultiStateViewCreate(view: View) {
        tvLoadingMsg = view.findViewById(R.id.tv_loading_msg)
        setLoadingMsg(MultiStatePage.config.loadingMsg)
    }

    fun setLoadingMsg(loadingMsg: String) {
        tvLoadingMsg.text = loadingMsg
    }
}