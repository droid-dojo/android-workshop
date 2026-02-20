package ninja.droiddojo.rickandmorty.character.data.api

import kotlinx.serialization.Serializable

@Serializable
data class PagingInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)