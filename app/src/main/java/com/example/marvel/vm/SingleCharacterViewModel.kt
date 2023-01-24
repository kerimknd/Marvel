package com.example.marvel.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.marvel.repository.characterdetail.CharacterDetailRepository
import io.reactivex.disposables.CompositeDisposable
import com.example.marvel.models.characters.*
import com.example.marvel.repository.NetworkState

class SingleCharacterViewModel(private val characterRepository: CharacterDetailRepository, characterId:String):
    ViewModel()  {
    private val compositeDisposable= CompositeDisposable()
    val characterDetail: LiveData<Character> by lazy {
        characterRepository.fetchSingleCharacterDetails(compositeDisposable,characterId)

    }
    val networkState: LiveData<NetworkState> by lazy {
        characterRepository.getCharacterDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}