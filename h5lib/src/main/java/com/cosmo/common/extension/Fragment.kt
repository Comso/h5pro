package com.cosmo.common.extension


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import kotlin.reflect.KClass


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
        beginTransaction().func().commit()

fun <T : FragmentActivity> Fragment.start(activity: KClass<T>) {
    startActivity(Intent(context, activity.java))
}

inline fun Fragment.args(body: Bundle.() -> Unit) {
    val args = Bundle()
    args.body()
    arguments = args
}