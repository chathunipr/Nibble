package org.example.project

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.tooling.preview.Preview
import console.Console
import console.ConsoleView

private const val STEP_NANOS = 1_000_000_000L / 60

@Composable
@Preview
fun App() {
    val machine = remember { Console() }
    val focusRequester = remember { FocusRequester() }
    var frame by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        var last = 0L
        var accumulated = 0L
        while (true) {
            withFrameNanos { now ->
                if (last == 0L) last = now
                accumulated += now - last
                last = now

                var steps = 0
                while (accumulated >= STEP_NANOS && steps < 5) {
                    machine.update()
                    accumulated -= STEP_NANOS
                    steps++
                }
                if (steps == 5) accumulated = 0

                machine.draw()
                frame++
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .focusRequester(focusRequester)
            .focusable()
            .onKeyEvent { machine.controls.onKey(it) },
        contentAlignment = Alignment.Center,
    ) {
        ConsoleView(machine.screen, frame)
    }
}