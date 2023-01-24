package com.example.marvel.repository.comics

import androidx.lifecycle.LiveData
import com.example.marvel.api.DBInterface

import com.example.marvel.models.comics.Comic
import com.example.marvel.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class ComicsListRepository( private val apiService: DBInterface) {

    lateinit var comicDataSource: ComicsDataSource
    fun fetchLiveComicsList (compositeDisposable: CompositeDisposable, characterId:String): LiveData<List<Comic>> {
        comicDataSource = ComicsDataSource(apiService,compositeDisposable)
        comicDataSource.fetchCharacterComics(characterId)
        return  comicDataSource.characterComicsResponse


    }
    fun getNetworkState(): LiveData<NetworkState> {
    return comicDataSource.networkState

    }
}