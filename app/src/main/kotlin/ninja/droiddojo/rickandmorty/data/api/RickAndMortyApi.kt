package ninja.droiddojo.rickandmorty.data.api

import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): CharacterResponse
}