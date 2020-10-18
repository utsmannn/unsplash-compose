package com.utsman.unsplashcompose

import com.utsman.unsplashcompose.model.Result
import com.utsman.unsplashcompose.network.NetworkInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoRepository(private val scope: CoroutineScope) {
    private val instance = NetworkInstance.create()

    private val _photo = MutableStateFlow(emptyList<Result>())
    private var page = 1
    val photos: StateFlow<List<Result>> get() = _photo

    fun getPhotos() = scope.launch {
        logi("trying get data -----")
        val addLoading = _photo.value.toMutableList()
        addLoading.add(Result.Loading)
        try {
            delay(2000)
            val photo = instance.photos(page)
            val added = _photo.value.toMutableList()
            added.addAll(photo)
            _photo.value = added
            page += 1
            logi("....... page $page loaded ......")
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
}