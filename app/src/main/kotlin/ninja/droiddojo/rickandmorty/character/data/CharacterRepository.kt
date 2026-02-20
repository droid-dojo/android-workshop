package ninja.droiddojo.rickandmorty.character.data

import android.util.Log
import kotlinx.coroutines.delay
import ninja.droiddojo.rickandmorty.character.data.api.CharacterDto
import ninja.droiddojo.rickandmorty.character.data.api.PlaceDto
import ninja.droiddojo.rickandmorty.character.data.api.RickAndMortyApi

class CharacterRepository(private val api: RickAndMortyApi) {
    suspend fun getCharacters(all: Boolean = false): List<Character> {
        val characters = mutableListOf<Character>()
        var hasMorePages = true
        var page = 1

        while (hasMorePages) {
            val response = api.getCharacters(page)
            characters.addAll(response.results.map { it.toDomain() })
            hasMorePages = all && response.info.next != null
            if (hasMorePages) {
                page += 1
                // Throttle da ansonsten die API 429 Fehler wirft wegen zu vielen anfragen
                delay(500)
            }
        }

        return characters
    }

    suspend fun getCharacter(id: Int): Character {
        return api.getCharacter(id).toDomain()
    }

    private fun CharacterDto.toDomain(): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            imageUrl = image,
            species = species,
            gender = gender,
            origin = origin.toDomain(),
            location = location.toDomain()
        )
    }

    private fun PlaceDto.toDomain(): Place? {
        if(url.isBlank() || name == "unknown") return null

        return try {
            Place(
                id = url.removePrefix("https://rickandmortyapi.com/api/location/").toInt(),
                name = name
            )
        } catch (e: NumberFormatException) {
            Log.e("CharacterRepository", "Invalid URL format: $url for location: $name", e)
            null
        }
    }
}