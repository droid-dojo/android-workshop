package ninja.droiddojo.rickandmorty.character.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int? = null
    ): CharacterListResponse

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterDto
}

