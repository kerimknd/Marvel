package com.example.marvel.repository.characterdetail

import androidx.lifecycle.LiveData
import com.example.marvel.api.DBInterface
import io.reactivex.disposables.CompositeDisposable
import com.example.marvel.models.characters.Character
import com.example.marvel.repository.NetworkState

class CharacterDetailRepository(private val apiService:DBInterface) {
    lateinit var characterDetailDataSource: CharacterDetailDataSource
    fun fetchSingleCharacterDetails(compositeDisposable: CompositeDisposable,characterId:String):LiveData<Character>{
        characterDetailDataSource = CharacterDetailDataSource(apiService,compositeDisposable)
        characterDetailDataSource.fetchSingleCharacterDetails(characterId = characterId)
        return characterDetailDataSource.characterDetailResponse
    }
    fun getCharacterDetailsNetworkState():LiveData<NetworkState>{
        return characterDetailDataSource.networkState
    }


}