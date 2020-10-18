package com.utsman.unsplashcompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class PhotoViewModel : ViewModel() {
    private val repository = PhotoRepository(viewModelScope)
    val statePhoto = repository.photos

    fun getPhotos() = repository.getPhotos()

    init {
        getPhotos()
    }
}