package ninja.droiddojo.rickandmorty.character.data.api

import kotlinx.serialization.Serializable

@Serializable
data class CharacterListResponse(
    val info: PagingInfo,
    val results: List<CharacterDto>
)

