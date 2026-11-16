package console

import kotlin.math.abs

class PongGame : Game {
    override var exitRequested = false
    private companion object {
        const val PADDLE_W = 3
        const val PADDLE_H = 20
        const val BALL = 3
        const val PLAYER_X = 4
        const val AI_X = SCREEN_SIZE - 4 - PADDLE_W
        const val WIN_SCORE = 7
    }

    private var playerY = (SCREEN_SIZE - PADDLE_H) / 2
    private var aiY = (SCREEN_SIZE - PADDLE_H) / 2
    private var ballX = 0
    private var ballY = 0
    private var dx = 0
    private var dy = 0
    private var playerScore = 0
    private var aiScore = 0

    init {
        resetBall(direction = 1)
    }

    private fun resetBall(direction: Int) {
        ballX = SCREEN_SIZE / 2
        ballY = SCREEN_SIZE / 2
        dx = 2 * direction
        dy = 1
    }

    /** Where the ball hits the paddle decides the bounce angle. */
    private fun bounceAngle(paddleY: Int): Int {
        val offset = (ballY + BALL / 2) - (paddleY + PADDLE_H / 2)
        return (offset / 5).coerceIn(-3, 3)
    }

    private fun overlapsPaddle(paddleX: Int, paddleY: Int): Boolean =
        ballX + BALL >= paddleX && ballX <= paddleX + PADDLE_W &&
                ballY + BALL >= paddleY && ballY <= paddleY + PADDLE_H

    override fun update(controls: Controls) {
        if (controls.bPressed) {
            exitRequested = true
            return
        }
        // Player movement
        if (controls.up) playerY -= 2
        if (controls.down) playerY += 2
        playerY = playerY.coerceIn(0, SCREEN_SIZE - PADDLE_H)

        // Simple AI: slower than the player, so it can be beaten
        val target = ballY - PADDLE_H / 2
        if (aiY < target) aiY += 1 else if (aiY > target) aiY -= 1
        aiY = aiY.coerceIn(0, SCREEN_SIZE - PADDLE_H)

        // Ball movement
        ballX += dx
        ballY += dy

        // Bounce off top and bottom
        if (ballY <= 0) {
            ballY = 0
            dy = abs(dy)
        } else if (ballY + BALL >= SCREEN_SIZE) {
            ballY = SCREEN_SIZE - BALL
            dy = -abs(dy)
        }

        // Paddle hits
        if (dx < 0 && overlapsPaddle(PLAYER_X, playerY)) {
            dx = abs(dx)
            dy = bounceAngle(playerY)
        } else if (dx > 0 && overlapsPaddle(AI_X, aiY)) {
            dx = -abs(dx)
            dy = bounceAngle(aiY)
        }

        // Scoring
        if (ballX + BALL < 0) {
            aiScore++
            resetBall(direction = 1)
        } else if (ballX > SCREEN_SIZE) {
            playerScore++
            resetBall(direction = -1)
        }

        if (playerScore >= WIN_SCORE || aiScore >= WIN_SCORE) {
            playerScore = 0
            aiScore = 0
        }
    }

    override fun draw(screen: Screen) {
        screen.clear(0)

        // Center line
        for (y in 0 until SCREEN_SIZE step 8) {
            screen.fillRect(SCREEN_SIZE / 2 - 1, y, 2, 4, 14)
        }

        // Paddles and ball
        screen.fillRect(PLAYER_X, playerY, PADDLE_W, PADDLE_H, 12)
        screen.fillRect(AI_X, aiY, PADDLE_W, PADDLE_H, 12)
        screen.fillRect(ballX, ballY, BALL, BALL, 4)

        // Scores
        screen.drawNumber(playerScore, 40, 4, 12, scale = 2)
        screen.drawNumber(aiScore, SCREEN_SIZE - 46, 4, 12, scale = 2)
    }
}