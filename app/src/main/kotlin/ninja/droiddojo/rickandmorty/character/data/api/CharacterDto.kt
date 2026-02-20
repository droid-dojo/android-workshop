package ninja.droiddojo.rickandmorty.character.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val image: String,
    val species: String,
    val gender: String,
    val origin: PlaceDto,
    val location: PlaceDto,
    val episode: List<String>
)

