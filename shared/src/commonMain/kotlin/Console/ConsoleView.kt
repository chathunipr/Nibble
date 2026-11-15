package console

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

@Composable
fun ConsoleView(screen: Screen, frame: Int, modifier: Modifier = Modifier) {
    Canvas(modifier.aspectRatio(1f)) {
        frame // reading it makes the canvas redraw each new frame
        val cell = size.width / SCREEN_SIZE
        for (y in 0 until SCREEN_SIZE) {
            for (x in 0 until SCREEN_SIZE) {
                drawRect(
                    color = PALETTE[screen.getPixel(x, y)],
                    topLeft = Offset(x * cell, y * cell),
                    size = Size(cell, cell),
                )
            }
        }
    }
}