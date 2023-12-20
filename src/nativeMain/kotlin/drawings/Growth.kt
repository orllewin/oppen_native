package drawings

import oppen.SVGDrawing
import oppen.SVG
import oppen.shapes.primitives.Line
import oppen.shapes.primitives.Point
import oppen.stdlib.Degree
import oppen.stdlib.Math.deg
import oppen.stdlib.Math.randomDegree
import oppen.stdlib.Radian
import oppen.stdlib.Random.random

@ExperimentalUnsignedTypes
class Growth: SVGDrawing(){

    override val label = "Growth"

    private val width = 600
    private val height = 600
    private val background = "#ffffff"

    private val svg = SVG(width, height, background)
    private var reduction = 1.5f

    override fun initialise() {

    }

    override fun draw() {

        val c = Point(width/2, height/2)
        val length = 100f

        reduction = random(1.2f, 1.9f)

        repeat(random(4, 8)){
            grow(c, length, randomDegree())
        }

        println(svg.build())
    }

    private fun grow(p: Point, length: Float, angle: Degree) {
        if (length < 2) return

        val p2 = p.plot(angle, length)
        svg.append(Line(p, p2))

        val i = random(2, 4)
        repeat(i) {
            val a: Radian = random(-12f, 12f).toDouble()
            grow(p2, length / reduction, a.deg())
        }
    }
}