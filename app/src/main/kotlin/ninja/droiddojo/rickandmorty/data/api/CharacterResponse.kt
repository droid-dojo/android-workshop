package ninja.droiddojo.rickandmorty.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val results: List<CharacterDto>
)