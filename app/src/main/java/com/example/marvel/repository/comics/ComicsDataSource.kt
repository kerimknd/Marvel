package com.example.marvel.repository.comics

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.api.DBInterface
import com.example.marvel.models.comics.Comic
import com.example.marvel.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ComicsDataSource(private val apiService : DBInterface, private val compositeDisposable: CompositeDisposable)
    {
       private val _networkState: MutableLiveData<NetworkState> = MutableLiveData()
        val networkState:LiveData<NetworkState>
        get()=_networkState
        private val _characterComicsResponse = MutableLiveData<List<Comic>>()
        val characterComicsResponse:LiveData<List<Comic>>
        get() = _characterComicsResponse

     fun fetchCharacterComics(characterId:String) {
        _networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getCharacterComics(characterId = characterId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        _characterComicsResponse.postValue(it.data.comicList)
                        _networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("ComicDateSource", it.message.toString())
                    }
                )
        )
    }


}