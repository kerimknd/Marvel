package com.example.marvel.repository.characters

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.marvel.api.DBInterface
import com.example.marvel.models.characters.Character
import com.example.marvel.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterDataSource(private val apiService : DBInterface, private val compositeDisposable: CompositeDisposable)
    : PageKeyedDataSource<Int, Character>() {
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getAllCharacters()
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.data.characterList, null,1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("CharacterDateSource", it.message.toString())
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        networkState.postValue(NetworkState.LOADING)

            compositeDisposable.add(
                apiService.getAllCharacters(limit = params.requestedLoadSize, offset = params.key*params.requestedLoadSize )
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {

                            var count=  (it.data.total/params.requestedLoadSize)

                            if(count >= params.key) {
                                callback.onResult(it.data.characterList,
                                    params.key+1 )
                                networkState.postValue(NetworkState.LOADED)
                            }
                            else{
                                networkState.postValue(NetworkState.ENDOFLIST)


                            }
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            Log.e("CharacterDateSource", it.message.toString())
                        }
                    )
            )

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {

    }


}