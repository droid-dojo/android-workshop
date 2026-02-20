package ninja.droiddojo.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ninja.droiddojo.rickandmorty.character.detail.CharacterDetailRoute
import ninja.droiddojo.rickandmorty.character.detail.CharacterDetailScreen
import ninja.droiddojo.rickandmorty.character.detail.navigateToCharacterDetail
import ninja.droiddojo.rickandmorty.character.list.CharacterListRoute
import ninja.droiddojo.rickandmorty.character.list.CharacterListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RickAndMortyTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = CharacterListRoute) {
                    composable<CharacterListRoute> {
                        CharacterListScreen(
                            onCharacterClick = navController::navigateToCharacterDetail
                        )
                    }
                    composable<CharacterDetailRoute> {
                        CharacterDetailScreen(onNavigateBack = navController::navigateUp)
                    }
                }
            }
        }
    }
}