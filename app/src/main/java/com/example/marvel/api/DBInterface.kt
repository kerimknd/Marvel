package com.example.marvel.api

import com.example.marvel.models.characters.CharacterModel
import com.example.marvel.models.comics.ComicModel
import com.example.marvel.utils.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DBInterface {

  @GET("characters")
   fun getAllCharacters(
      @Query("limit") limit: Int? = 30,
      @Query("offset") offset: Int? = 0,
      @Query("ts")ts:String= Constants.timeStamp,
      @Query("hash")hash:String=Constants.hash(),
     ):Single<CharacterModel>

   @GET("characters/{characterId}")
   fun getCharacterById(
        @Path("characterId")characterId:String,
        @Query("ts")ts:String=Constants.timeStamp,
        @Query("hash")hash:String=Constants.hash(),
    ):Single<CharacterModel>


    @GET("characters/{characterId}/comics")
     fun getCharacterComics(
        @Path("characterId") characterId: String,
        @Query("ts")ts:String=Constants.timeStamp,
        @Query("hash")hash:String=Constants.hash(),
    ): Single<ComicModel>
}