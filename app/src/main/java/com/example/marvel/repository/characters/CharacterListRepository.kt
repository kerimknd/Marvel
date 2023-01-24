package com.example.marvel.repository.characters

import androidx.lifecycle.LiveData

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList

import com.example.marvel.api.DBInterface

import com.example.marvel.models.characters.Character
import com.example.marvel.repository.NetworkState
import com.example.marvel.utils.Constants
import io.reactivex.disposables.CompositeDisposable

class CharacterListRepository( private val apiService:DBInterface) {
    lateinit var characterList:LiveData<PagedList<Character>>
    lateinit var characterDataSourceFactory: CharacterDataSourceFactory
    fun fetchLiveCharacterList (compositeDisposable: CompositeDisposable):LiveData<PagedList<Character>>{
        characterDataSourceFactory = CharacterDataSourceFactory(apiService,compositeDisposable)
        val config : PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.POST_PER_PAGE)
            .build()

        characterList = LivePagedListBuilder(characterDataSourceFactory,config).build()
        return characterList
    }
    fun getNetworkState(): LiveData<NetworkState>{
        return Transformations.switchMap<CharacterDataSource, NetworkState>(
            characterDataSourceFactory.charactersLiveDataSource, CharacterDataSource::networkState
        )
    }
}