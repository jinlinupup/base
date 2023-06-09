package com.gankao.crosssdk

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import cn.jinlin.lib_statepage.MultiStateContainer
import cn.jinlin.lib_statepage.state.EmptyState
import cn.jinlin.lib_statepage.state.ErrorState
import cn.jinlin.lib_statepage.state.LoadingState
import cn.jinlin.lib_statepage.state.SuccessState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @ProjectName: MultiStatePage
 * @Author: 赵岩
 * @Email: 17635289240@163.com
 * @Description: TODO
 * @CreateDate: 2020/9/17 15:04
 */
fun mockRandom(multiStateContainer: MultiStateContainer, block: () -> Unit) {
    MainScope().launch {
        multiStateContainer.show<LoadingState>()
        val delayTime = (10..30).random() * 100.toLong()
        delay(delayTime)
        block.invoke()
        when ((1..3).random()) {
            1 -> multiStateContainer.show<SuccessState>()
            2 -> multiStateContainer.show<EmptyState>()
            3 -> multiStateContainer.show<ErrorState>()
        }
    }
}

fun mockError(multiStateContainer: MultiStateContainer) {
    MainScope().launch {
        multiStateContainer.show<LoadingState>()
        val delayTime = (10..30).random() * 100.toLong()
        delay(delayTime)
//        val errorState = ErrorState()
//        errorState.retry { mockSuccess(multiStateContainer) }
        multiStateContainer.show<ErrorState> {
            it.retry { mockSuccess(multiStateContainer) }
        }
    }
}

fun mockSuccess(multiStateContainer: MultiStateContainer) {
    MainScope().launch {
        multiStateContainer.show<LoadingState>()
        val delayTime = (10..30).random() * 100.toLong()
        delay(delayTime)
        multiStateContainer.show<SuccessState>()
    }
}

fun mockEmpty(multiStateContainer: MultiStateContainer) {
    MainScope().launch {
        multiStateContainer.show<LoadingState>()
        val delayTime = (10..30).random() * 100.toLong()
        delay(delayTime)
        multiStateContainer.show<EmptyState>()
    }
}

fun MultiStateContainer.showSuccess(callBack: () -> Unit = {}) {
    show<SuccessState> {
        callBack.invoke()
    }
}

fun MultiStateContainer.showError(callBack: (ErrorState) -> Unit = {}) {
    show<ErrorState> {
        callBack.invoke(it)
    }
}

fun MultiStateContainer.showEmpty(callBack: () -> Unit = {}) {
    show<EmptyState> {
        callBack.invoke()
    }
}

fun MultiStateContainer.showLoading(callBack: () -> Unit = {}) {
    show<LoadingState> {
        callBack.invoke()
    }
}

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

fun <T : ViewBinding> Activity.inflate(inflater: (LayoutInflater) -> T) = lazy {
    inflater(layoutInflater).apply { setContentView(root) }
}

