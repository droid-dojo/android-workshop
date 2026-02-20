package ninja.droiddojo.rickandmorty

import kotlinx.serialization.json.Json
import ninja.droiddojo.rickandmorty.data.CharacterRepository
import ninja.droiddojo.rickandmorty.data.api.RickAndMortyApi
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object Dependencies {
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val rickAndMortyApi: RickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)

    val characterRepository = CharacterRepository(rickAndMortyApi)
}
