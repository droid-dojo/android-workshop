package ninja.droiddojo.rickandmorty.data.api

import kotlinx.serialization.Serializable
import ninja.droiddojo.rickandmorty.Character

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val image: String
)

fun CharacterDto.toDomain(): Character = Character(
    id = id,
    name = name,
    status = status,
    imageUrl = image
)
