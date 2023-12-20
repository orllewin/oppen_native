package drawings

import oppen.Drawing
import oppen.stdlib.Random.random

@ExperimentalUnsignedTypes
class SDLDrawingTest: Drawing(600, 600) {

    var y = 0

    override fun draw() {
        background(0)
        stroke(random(255), random(255), random(255))
        line(0, y, width, y)
        if(y == height) exit()
        y++
    }

    override fun key(char: Char?) {
        println("key: $char")
    }
}