package com.example.marvel.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.marvel.repository.characters.CharacterListRepository
import com.example.marvel.models.characters.Character
import com.example.marvel.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class CharactersViewModel(private val characterRepository: CharacterListRepository): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val characterPagedList: LiveData<PagedList<Character>> by lazy {
        characterRepository.fetchLiveCharacterList(compositeDisposable)

    }
    val networkState : LiveData<NetworkState> by lazy {
        characterRepository.getNetworkState()
    }
    fun listIsEmty():Boolean{
        return  characterPagedList.value?.isEmpty() ?:true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}