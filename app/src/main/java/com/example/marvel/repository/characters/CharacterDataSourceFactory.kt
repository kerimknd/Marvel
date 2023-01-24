package com.example.marvel.repository.characters

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.marvel.api.DBInterface
import com.example.marvel.models.characters.Character
import io.reactivex.disposables.CompositeDisposable

class CharacterDataSourceFactory(private val apiService:DBInterface, private val compositeDisposable: CompositeDisposable) :DataSource.Factory<Int, Character>()  {
    val charactersLiveDataSource = MutableLiveData<CharacterDataSource>()
    override fun create(): DataSource<Int, Character> {
        val characterDataSource = CharacterDataSource(apiService,compositeDisposable)

        charactersLiveDataSource.postValue(characterDataSource)
        return characterDataSource

    }

}