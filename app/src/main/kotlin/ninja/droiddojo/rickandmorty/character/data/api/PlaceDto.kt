package ninja.droiddojo.rickandmorty.character.data.api

import kotlinx.serialization.Serializable

@Serializable
data class PlaceDto(
    val name: String,
    val url: String
)