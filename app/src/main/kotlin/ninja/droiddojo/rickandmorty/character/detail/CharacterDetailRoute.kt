package ninja.droiddojo.rickandmorty.character.detail

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailRoute(val id: Int)

fun NavHostController.navigateToCharacterDetail(id: Int) {
    navigate(CharacterDetailRoute(id))
}