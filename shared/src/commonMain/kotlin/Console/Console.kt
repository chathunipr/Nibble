package console

class Console {
    val screen = Screen()
    val controls = Controls()
    private var game: Game = MenuGame()

    fun update() {
        game.update(controls)

        val current = game
        if (current is MenuGame) {
            current.chosen?.let { game = createGame(it) }
        } else if (current.exitRequested) {
            game = MenuGame()
        }

        controls.endFrame()
    }

    fun draw() = game.draw(screen)

    private fun createGame(index: Int): Game = when (index) {
        0 -> PongGame()
        1 -> SnakeGame()
        2 -> PuzzleGame()
        else -> CodePuzzleGame()
    }
}