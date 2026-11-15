package console

const val SCREEN_SIZE = 128

class Screen {
    private val pixels = ByteArray(SCREEN_SIZE * SCREEN_SIZE)

    fun clear(color: Int = 0) = pixels.fill(color.toByte())

    fun setPixel(x: Int, y: Int, color: Int) {
        if (x in 0 until SCREEN_SIZE && y in 0 until SCREEN_SIZE) {
            pixels[y * SCREEN_SIZE + x] = color.toByte()
        }
    }

    fun getPixel(x: Int, y: Int): Int = pixels[y * SCREEN_SIZE + x].toInt()

    fun fillRect(x: Int, y: Int, w: Int, h: Int, color: Int) {
        for (j in y until y + h) {
            for (i in x until x + w) {
                setPixel(i, j, color)
            }
        }
    }

}

