package com.cosmo.common.extension

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

val ioThread get() = Schedulers.io()
val mainThread: Scheduler get() = AndroidSchedulers.mainThread()