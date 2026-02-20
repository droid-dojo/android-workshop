package ninja.droiddojo.rickandmorty

sealed interface UiState {
    data object Loading : UiState
    data class Success(val characters: List<Character>) : UiState
    data class Error(val message: String) : UiState
}
