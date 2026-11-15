package console

class MenuGame : Game {
    private val items = listOf("PONG", "SNAKE", "SLIDE", "LOOPY")
    private var selected = 0

    /** Index of the game the player picked, or null while still choosing. */
    var chosen: Int? = null
        private set

    override fun update(controls: Controls) {
        if (controls.upPressed) selected = (selected + items.size - 1) % items.size
        if (controls.downPressed) selected = (selected + 1) % items.size
        if (controls.aPressed) chosen = selected
    }

    override fun draw(screen: Screen) {
        screen.clear(0)
        screen.drawTextCentered("NIBBLE", 10, 4, scale = 3)
        screen.drawTextCentered("FANTASY CONSOLE", 30, 14)

        items.forEachIndexed { i, name ->
            val y = 46 + i * 14
            val isSelected = i == selected
            if (isSelected) screen.drawChar('>', 30, y, 4, scale = 2)
            screen.drawText(name, 44, y, if (isSelected) 12 else 14, scale = 2)
        }

        screen.drawTextCentered("UP DOWN  Z PLAY", 112, 13)
    }
}