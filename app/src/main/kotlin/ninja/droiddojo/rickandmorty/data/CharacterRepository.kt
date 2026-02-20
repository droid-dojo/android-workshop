package ninja.droiddojo.rickandmorty.data

import ninja.droiddojo.rickandmorty.Character
import ninja.droiddojo.rickandmorty.data.api.CharacterDto
import ninja.droiddojo.rickandmorty.data.api.RickAndMortyApi

class CharacterRepository(private val api: RickAndMortyApi) {
    suspend fun getCharacters(): List<Character> {
        return api.getCharacters().results.map { it.toDomain() }
    }

    private fun CharacterDto.toDomain(): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            imageUrl = image
        )
    }
}

