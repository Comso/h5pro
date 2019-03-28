package com.cosmo.common.extension

import com.cosmo.common.rx.BaseEvent
import com.cosmo.common.rx.RxBus
import io.reactivex.disposables.CompositeDisposable
import kotlin.reflect.KClass

/**
 *Author:ruchao.jiang
 *Created: 2019/3/14 14:29
 *Email:ruchao.jiang@uama.com.cn
 */



interface EventResultListener<T>{
    fun onEvent(result: T)
}

fun <T:BaseEvent> CompositeDisposable.addDisposable(event: KClass<T>,result: EventResultListener<T>?){
    add(RxBus
            .getInstance()
            .toFlowable(event.java)
            .observeOn(mainThread)
            .subscribe { result?.onEvent(it) })
}

class MainEvent :BaseEvent()


