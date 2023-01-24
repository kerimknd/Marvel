package com.example.marvel.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.models.comics.Comic
import com.example.marvel.repository.comics.ComicsListRepository
import com.example.marvel.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ComicsViewModel (private val comicsRepository: ComicsListRepository, val characterId:String): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val comicPagedList: LiveData<List<Comic>> by lazy {
      comicsRepository.fetchLiveComicsList(compositeDisposable,characterId)

    }

    val networkState : LiveData<NetworkState> by lazy {
        comicsRepository.getNetworkState()
    }
    fun listIsEmty():Boolean{
        return  comicPagedList.value?.isEmpty() ?:true
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}