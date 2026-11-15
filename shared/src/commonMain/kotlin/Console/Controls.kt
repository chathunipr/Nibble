package console

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type

class Controls {
    var up = false
    var down = false
    var left = false
    var right = false
    var a = false
    var b = false

    private var prevUp = false
    private var prevDown = false
    private var prevLeft = false
    private var prevRight = false
    private var prevA = false
    private var prevB = false

    val upPressed: Boolean get() = up && !prevUp
    val downPressed: Boolean get() = down && !prevDown
    val leftPressed: Boolean get() = left && !prevLeft
    val rightPressed: Boolean get() = right && !prevRight
    val aPressed: Boolean get() = a && !prevA
    val bPressed: Boolean get() = b && !prevB

    /** Call once after every game update. */
    fun endFrame() {
        prevUp = up
        prevDown = down
        prevLeft = left
        prevRight = right
        prevA = a
        prevB = b
    }

    /** Returns true if the key was one we handle. */
    fun onKey(event: KeyEvent): Boolean {
        val pressed = when (event.type) {
            KeyEventType.KeyDown -> true
            KeyEventType.KeyUp -> false
            else -> return false
        }
        when (event.key) {
            Key.DirectionUp, Key.W -> up = pressed
            Key.DirectionDown, Key.S -> down = pressed
            Key.DirectionLeft, Key.A -> left = pressed
            Key.DirectionRight, Key.D -> right = pressed
            Key.Z, Key.Spacebar -> a = pressed
            Key.X -> b = pressed
            else -> return false
        }
        return true
    }
}