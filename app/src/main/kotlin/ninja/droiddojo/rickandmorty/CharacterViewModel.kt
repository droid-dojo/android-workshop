package ninja.droiddojo.rickandmorty

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterViewModel : ViewModel() {
    private val _characters = MutableStateFlow(getDummyCharacters())
    val characters: StateFlow<List<Character>> = _characters.asStateFlow()

    fun toggleFavorite(characterId: Int) {
        _characters.update { list ->
            list.map { character ->
                if (character.id == characterId) {
                    character.copy(isFavorite = !character.isFavorite)
                } else {
                    character
                }
            }
        }
    }
}