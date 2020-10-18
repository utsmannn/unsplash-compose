@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.utsman.unsplashcompose

import android.util.Log
import com.google.gson.GsonBuilder

fun logi(msg: String?) = Log.i("UNSPLASH_COMPOSE", msg)

fun Any.toJson(): String {
    val gson = GsonBuilder()
        .setPrettyPrinting()
        .create()
    return gson.toJson(this)
}