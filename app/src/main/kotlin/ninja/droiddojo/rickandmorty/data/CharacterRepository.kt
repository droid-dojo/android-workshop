package ninja.droiddojo.rickandmorty.data

import ninja.droiddojo.rickandmorty.Character
import ninja.droiddojo.rickandmorty.data.api.RickAndMortyApi
import ninja.droiddojo.rickandmorty.data.api.toDomain

class CharacterRepository(private val api: RickAndMortyApi) {
    suspend fun getCharacters(): List<Character> {
        return api.getCharacters().results.map { it.toDomain() }
    }
}
