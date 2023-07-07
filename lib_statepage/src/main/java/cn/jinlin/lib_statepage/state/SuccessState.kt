package cn.jinlin.lib_statepage.state

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import cn.jinlin.lib_statepage.MultiState
import cn.jinlin.lib_statepage.MultiStateContainer
import cn.jinlin.lib_statepage.R

/**
 * @ProjectName: MultiStatePage
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2020/9/17 14:11
 */
class SuccessState : MultiState() {
    override fun onCreateMultiStateView(
        context: Context,
        inflater: LayoutInflater,
        container: MultiStateContainer
    ): View {
        return View(context)
    }

    override fun onMultiStateViewCreate(view: View) = Unit

}