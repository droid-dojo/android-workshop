package ninja.droiddojo.rickandmorty.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val image: String
)

