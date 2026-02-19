package ninja.droiddojo.rickandmorty

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val imageUrl: String
)

fun getDummyCharacters(): List<Character> {
    return listOf(
        Character(
            1,
            "Rick Sanchez",
            "Alive",
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        ),
        Character(
            2,
            "Morty Smith",
            "Alive",
            "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
        ),
        Character(
            3,
            "Summer Smith",
            "Alive",
            "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
        ),
        Character(
            4,
            "Beth Smith",
            "Alive",
            "https://rickandmortyapi.com/api/character/avatar/4.jpeg"
        ),
        Character(
            5,
            "Jerry Smith",
            "Alive",
            "https://rickandmortyapi.com/api/character/avatar/5.jpeg"
        ),
    )
}