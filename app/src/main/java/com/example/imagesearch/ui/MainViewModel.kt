package com.example.imagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.data.ImageDocuments
import com.example.imagesearch.data.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _thumbnails = MutableLiveData<MutableList<Thumbnail>>(mutableListOf())
    val thumbnails: LiveData<MutableList<Thumbnail>> = _thumbnails

    private val _imageDocuments = MutableLiveData<List<ImageDocuments>>()
    val imageDocuments: LiveData<List<ImageDocuments>> = _imageDocuments

    fun searchImages(query: String, sort: String, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.searchImages(query, sort, page, size)
            _imageDocuments.value = result
        }
    }

    fun addThumbnail(thumbnail: Thumbnail) {
        _thumbnails.value?.let {
            it.add(thumbnail)
            _thumbnails.value = it
        }
    }

    fun removeThumbnail(thumbnail: Thumbnail) {
        _thumbnails.value?.let {
            it.remove(thumbnail)
            _thumbnails.value = it
        }
    }
}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
