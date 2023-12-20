package drawings

import oppen.Drawing
import oppen.shapes.primitives.Line
import oppen.shapes.primitives.Point
import oppen.shapes.primitives.Rectangle
import oppen.stdlib.Degree
import oppen.stdlib.Math
import oppen.stdlib.Math.deg
import oppen.stdlib.Math.map
import oppen.stdlib.Radian
import oppen.stdlib.Random.random

private val lines = mutableListOf<Line>()
private val overlayRect = Rectangle(0, 0, 600, 600)
const val MAX_LINES = 100000f

@ExperimentalUnsignedTypes
class GrowthSDL: Drawing(600, 600) {

    private var reduction = 1.5f

    override fun initialise() {
        spawnNew()
        background(255)
        stroke(0, 32)
        fill(230, 75)
        super.initialise()
    }

    override fun draw() {
        lines.forEach { line ->
            line(line.x1, line.y1, line.x2, line.y2)
        }
        rect(overlayRect)
    }

    override fun key(char: Char?) {
        when(char){
            'N' -> {
                lock()
                spawnNew()
                unlock()
            }
        }
    }

    private fun spawnNew(){
        lines.clear()

        val c = Point(width/2, height/2)
        val length = 100f

        reduction = random(1.6f, 2.2f)

        repeat(random(4, 8)){
            grow(c, length, Math.randomDegree())
        }

        when {
            lines.size < MAX_LINES/3 -> spawnNew()
            else -> {
                val lines = lines.size
                val opacity = map(lines.toFloat(), 0f, MAX_LINES, 128f, 45f)
                strokeOpacity(opacity.toInt())
            }
        }
    }

    private fun grow(p: Point, length: Float, angle: Degree) {
        if (length < 2 || lines.size > MAX_LINES) return

        val p2 = p.plot(angle, length)

        lines.add(Line(p, p2))

        val i = random(3, 8)
        var a: Radian = 0.0
        repeat(i) {
            a += random(-6f, 6f).toDouble()
            grow(p2, length / reduction, a.deg())
        }
    }
}