package ninja.droiddojo.rickandmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ninja.droiddojo.rickandmorty.data.CharacterRepository

class CharacterListViewModel : ViewModel() {
    private val repository: CharacterRepository = Dependencies.characterRepository
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.update {
                try {
                    val characters = repository.getCharacters()
                    UiState.Success(characters)
                } catch (e: Exception) {
                    UiState.Error(e.message ?: "An unknown error occurred")
                }
            }
        }
    }

    fun toggleFavorite(characterId: Int) {
        _uiState.update { state ->
            when (state) {
                is UiState.Success -> {
                    val updatedCharacters = state.characters.map { character ->
                        if (character.id == characterId) {
                            character.copy(isFavorite = !character.isFavorite)
                        } else {
                            character
                        }
                    }
                    state.copy(characters = updatedCharacters)
                }
                else -> state
            }
        }
    }
}