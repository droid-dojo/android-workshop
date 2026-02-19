package ninja.droiddojo.rickandmorty

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Rick & Morty Guide") })
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(getDummyCharacters()) { character ->
                CharacterItem(
                    character = character,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@PreviewLightDark
@PreviewDynamicColors
@Composable
private fun CharacterListScreenPreview() {
    val previewHandler = AsyncImagePreviewHandler {
        ColorImage(Color.Red.toArgb())
    }

    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        RickAndMortyTheme {
            CharacterListScreen()
        }
    }
}
