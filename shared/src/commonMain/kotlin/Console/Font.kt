package console

private val GLYPHS: Map<Char, IntArray> = mapOf(
    '0' to intArrayOf(0b111, 0b101, 0b101, 0b101, 0b111),
    '1' to intArrayOf(0b010, 0b110, 0b010, 0b010, 0b111),
    '2' to intArrayOf(0b111, 0b001, 0b111, 0b100, 0b111),
    '3' to intArrayOf(0b111, 0b001, 0b111, 0b001, 0b111),
    '4' to intArrayOf(0b101, 0b101, 0b111, 0b001, 0b001),
    '5' to intArrayOf(0b111, 0b100, 0b111, 0b001, 0b111),
    '6' to intArrayOf(0b111, 0b100, 0b111, 0b101, 0b111),
    '7' to intArrayOf(0b111, 0b001, 0b001, 0b001, 0b001),
    '8' to intArrayOf(0b111, 0b101, 0b111, 0b101, 0b111),
    '9' to intArrayOf(0b111, 0b101, 0b111, 0b001, 0b111),

    'A' to intArrayOf(0b010, 0b101, 0b111, 0b101, 0b101),
    'B' to intArrayOf(0b110, 0b101, 0b110, 0b101, 0b110),
    'C' to intArrayOf(0b011, 0b100, 0b100, 0b100, 0b011),
    'D' to intArrayOf(0b110, 0b101, 0b101, 0b101, 0b110),
    'E' to intArrayOf(0b111, 0b100, 0b110, 0b100, 0b111),
    'F' to intArrayOf(0b111, 0b100, 0b110, 0b100, 0b100),
    'G' to intArrayOf(0b111, 0b100, 0b101, 0b101, 0b111),
    'H' to intArrayOf(0b101, 0b101, 0b111, 0b101, 0b101),
    'I' to intArrayOf(0b111, 0b010, 0b010, 0b010, 0b111),
    'J' to intArrayOf(0b001, 0b001, 0b001, 0b101, 0b010),
    'K' to intArrayOf(0b101, 0b101, 0b110, 0b101, 0b101),
    'L' to intArrayOf(0b100, 0b100, 0b100, 0b100, 0b111),
    'M' to intArrayOf(0b101, 0b111, 0b111, 0b101, 0b101),
    'N' to intArrayOf(0b110, 0b101, 0b101, 0b101, 0b101),
    'O' to intArrayOf(0b010, 0b101, 0b101, 0b101, 0b010),
    'P' to intArrayOf(0b110, 0b101, 0b110, 0b100, 0b100),
    'Q' to intArrayOf(0b010, 0b101, 0b101, 0b111, 0b011),
    'R' to intArrayOf(0b110, 0b101, 0b110, 0b101, 0b101),
    'S' to intArrayOf(0b011, 0b100, 0b010, 0b001, 0b110),
    'T' to intArrayOf(0b111, 0b010, 0b010, 0b010, 0b010),
    'U' to intArrayOf(0b101, 0b101, 0b101, 0b101, 0b111),
    'V' to intArrayOf(0b101, 0b101, 0b101, 0b101, 0b010),
    'W' to intArrayOf(0b101, 0b101, 0b111, 0b111, 0b101),
    'X' to intArrayOf(0b101, 0b101, 0b010, 0b101, 0b101),
    'Y' to intArrayOf(0b101, 0b101, 0b010, 0b010, 0b010),
    'Z' to intArrayOf(0b111, 0b001, 0b010, 0b100, 0b111),

    '>' to intArrayOf(0b100, 0b010, 0b001, 0b010, 0b100),
    '!' to intArrayOf(0b010, 0b010, 0b010, 0b000, 0b010),
    '-' to intArrayOf(0b000, 0b000, 0b111, 0b000, 0b000),
)

/** Width in pixels of [text] drawn at [scale]. */
fun textWidth(text: String, scale: Int = 1): Int = text.length * 4 * scale - scale

fun Screen.drawChar(ch: Char, x: Int, y: Int, color: Int, scale: Int = 1) {
    val rows = GLYPHS[ch.uppercaseChar()] ?: return // unknown chars (like spaces) draw nothing
    for (row in 0 until 5) {
        for (col in 0 until 3) {
            if ((rows[row] and (0b100 shr col)) != 0) {
                fillRect(x + col * scale, y + row * scale, scale, scale, color)
            }
        }
    }
}

fun Screen.drawText(text: String, x: Int, y: Int, color: Int, scale: Int = 1) {
    var cursorX = x
    for (ch in text) {
        drawChar(ch, cursorX, y, color, scale)
        cursorX += 4 * scale // 3 pixels of letter + 1 pixel gap
    }
}

fun Screen.drawTextCentered(text: String, y: Int, color: Int, scale: Int = 1) {
    drawText(text, (SCREEN_SIZE - textWidth(text, scale)) / 2, y, color, scale)
}

fun Screen.drawDigit(digit: Int, x: Int, y: Int, color: Int, scale: Int = 1) =
    drawChar('0' + digit.coerceIn(0, 9), x, y, color, scale)

fun Screen.drawNumber(value: Int, x: Int, y: Int, color: Int, scale: Int = 1) =
    drawText(value.toString(), x, y, color, scale)