package com.example.marvel.repository.characterdetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.marvel.api.DBInterface
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.example.marvel.models.characters.Character
import com.example.marvel.repository.NetworkState

class CharacterDetailDataSource (private val apiService:DBInterface,private val compositeDisposable: CompositeDisposable){
    private  val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get()=_networkState

    private val _characterDetailResponse = MutableLiveData<Character>()
    val characterDetailResponse: LiveData<Character>
        get() = _characterDetailResponse

    fun fetchSingleCharacterDetails(characterId:String){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getCharacterById(characterId = characterId)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _characterDetailResponse.postValue(it.data.characterList.first())
                            _networkState.postValue(NetworkState.LOADED)

                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("CharacterDetail",it.message.toString())
                        }
                    )
            )
        }catch (e: Exception){
            Log.e("CharacterDetail",e.message.toString())
        }
    }

}