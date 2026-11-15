package console

interface Game {
    /** A game sets this to true when it wants to go back to the menu. */
    val exitRequested: Boolean get() = false

    fun update(controls: Controls)
    fun draw(screen: Screen)
}