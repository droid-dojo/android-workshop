package ninja.droiddojo.rickandmorty.character.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ninja.droiddojo.rickandmorty.Dependencies
import ninja.droiddojo.rickandmorty.character.data.CharacterRepository

class CharacterDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository: CharacterRepository = Dependencies.characterRepository
    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    init {
        val arguments = savedStateHandle.toRoute<CharacterDetailRoute>()
        loadCharacter(arguments.id)
    }

    private fun loadCharacter(id: Int) {
        viewModelScope.launch {
            try {
                val character = repository.getCharacter(id)
                _uiState.value = CharacterDetailUiState.Success(character)
            } catch (e: Exception) {
                _uiState.value = CharacterDetailUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}