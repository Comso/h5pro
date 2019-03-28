package com.cosmo.common.extension


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import kotlin.reflect.KClass

/*inline fun <reified T : ViewModel> FragmentActivity.viewModel(factory: ViewModelProvider.Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}*/
const val COMMON_RECODE = 2019

fun <T : FragmentActivity> FragmentActivity.goAct(activity: KClass<T>) {
    val intent = Intent(this, activity.java)
    startActivity(intent)
}



fun <T : FragmentActivity> FragmentActivity.goAct(activity: KClass<T>,bundle: Bundle) {
    val intent = Intent(this, activity.java)
    intent.putExtras(bundle)
    startActivity(intent)
}


fun <T : FragmentActivity> FragmentActivity.goActForResult(activity: KClass<T>) {
    val intent = Intent(this, activity.java)
    startActivityForResult(intent, COMMON_RECODE)
}



fun <T : FragmentActivity> FragmentActivity.goActForResult(activity: KClass<T>,bundle: Bundle) {
    val intent = Intent(this, activity.java)
    intent.putExtras(bundle)
    startActivityForResult(intent, COMMON_RECODE)
}


fun FragmentActivity.getExtraString(key:String):String{
    return intent.getStringExtra(key)
}