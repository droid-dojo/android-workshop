package ninja.droiddojo.rickandmorty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler

@Composable
fun CharacterListScreen(
    viewModel: CharacterListViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CharacterListContent(
        state = state,
        onFavoriteClick = viewModel::toggleFavorite
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListContent(
    state: UiState,
    onFavoriteClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Rick & Morty Guide") })
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is UiState.Error -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = state.message
                    )
                }

                is UiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.characters) { character ->
                            CharacterItem(
                                character = character,
                                onFavoriteClick = { onFavoriteClick(character.id) },
                            )
                        }
                    }
                }
            }
        }
    }
}

private class CharacterListContentPreviewParameterProvider :
    CollectionPreviewParameterProvider<UiState>(
        listOf(
            UiState.Loading,
            UiState.Error("No Internet Connection"),
            UiState.Success(getDummyCharacters())
        )
    )


@OptIn(ExperimentalCoilApi::class)
@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun CharacterListContentPreview(
    @PreviewParameter(CharacterListContentPreviewParameterProvider::class) state: UiState
) {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Red.toArgb())
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        RickAndMortyTheme {
            CharacterListContent(
                state = state,
                onFavoriteClick = {}
            )
        }
    }
}
