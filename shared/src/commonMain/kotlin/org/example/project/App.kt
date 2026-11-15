package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import console.ConsoleView
import console.SCREEN_SIZE
import console.Screen
import androidx.compose.runtime.withFrameNanos
@Composable
@Preview
fun App() {
    val screen = remember { Screen() }
    var frame by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            withFrameNanos { }
            screen.clear(0)
            screen.setPixel(frame % SCREEN_SIZE, 64, 12)
            frame++
        }
    }

    ConsoleView(screen, frame, Modifier.fillMaxSize())
}