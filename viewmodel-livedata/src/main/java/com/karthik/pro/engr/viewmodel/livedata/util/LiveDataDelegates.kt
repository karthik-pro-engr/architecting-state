package com.karthik.pro.engr.viewmodel.livedata.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

// extension that returns a delegate exposing the liveData.value as a property
fun <T> MutableLiveData<T>.asDelegate() = object : ReadWriteProperty<Any?, T?> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T? = this@asDelegate.value
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        this@asDelegate.value = value // use postValue if setting from background thread
    }
}
