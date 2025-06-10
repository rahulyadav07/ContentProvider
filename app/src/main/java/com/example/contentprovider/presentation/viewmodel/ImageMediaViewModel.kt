package com.example.contentprovider.presentation.viewmodel

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contentprovider.data.MediaRepo
import com.example.contentprovider.domain.ImageMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageMediaViewModel:ViewModel() {

    private val _mediaFiles = MutableLiveData<List<ImageMedia>>()
    val mediaFiles: LiveData<List<ImageMedia>> get() = _mediaFiles

    fun loadMedia(repository: MediaRepo) {
        viewModelScope.launch(Dispatchers.IO) {
            val files = repository.getAllMDiaImage()
            _mediaFiles.postValue(files)
        }
    }
}