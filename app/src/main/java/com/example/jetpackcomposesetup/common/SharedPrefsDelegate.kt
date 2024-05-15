package com.example.jetpackcomposesetup.common


import com.example.jetpackcomposesetup.AppSharedPrefs
import kotlin.reflect.KProperty

class SharedPrefsDelegate<T> {
    @Suppress("UNCHECKED_CAST")
    operator fun getValue(thisRef: AppSharedPrefs, property: KProperty<*>): T {
        return thisRef.sharedPrefs.all[property.name] as T
    }

    operator fun setValue(thisRef: AppSharedPrefs, property: KProperty<*>, value: T) {
        thisRef.sharedPrefs.edit().apply {
            when (value) {
                is Boolean -> putBoolean(property.name, value)
                is Float -> putFloat(property.name, value)
                is Int -> putInt(property.name, value)
                is Long -> putLong(property.name, value)
                is String -> putString(property.name, value)
                else -> putString(property.name, null)
            }.apply()
        }
    }
}