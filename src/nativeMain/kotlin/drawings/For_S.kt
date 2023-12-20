package drawings

import oppen.SVGDrawing
import oppen.SVG
import oppen.shapes.primitives.CatmullRomSpline
import oppen.shapes.primitives.Point
import oppen.stdlib.Random
import platform.posix.pow
import kotlin.math.cos
import kotlin.math.sin

/**
 *
 * To make a grid using ImageMagick:
 * montage 001.png 002.png 003.png 004.png 005.png 006.png 007.png 008.png 009.png -geometry 900x900+3+3 hearts.png
 *
 */
@ExperimentalUnsignedTypes
class For_S: SVGDrawing() {

    override val label = "Valentines drawing"

    private val width = 900
    private val height = 900
    private val background = "#ffffff"
    private val svg = SVG(width, height, background)

    private val catmullRom = CatmullRomSpline(CatmullRomSpline.Type.CHORDAL, 10)

    private val speed = 0.05f
    private var xSeed = 0f
    private var ySeed = 0f

    var radius = 20

    private var angle = 0.0
    private var scaleFactor = 50.0f

    init {
        xSeed = Random.random(2f, 5f)
        ySeed = Random.random(2f)
    }

    override fun initialise() {

    }

    override fun draw() {

        val points = arrayListOf<Point>()

        repeat(730){ index ->
            val increment = index * 0.01

            val x = radius * 16 * pow(sin(increment), 3.0)
            val y = - radius * 1* (13 * cos(increment) - 5 * cos(2 * increment) - 2 * cos(3 * increment) - cos(4 * increment))

            points.add(Point(450 + x, 450 + y))
        }

        points.forEach { point ->

            val c = cos(angle / xSeed).toFloat()
            val s = sin(angle * ySeed).toFloat()

            val x1 = c * scaleFactor
            val y1 = s * scaleFactor

            val x2 = cos(angle / xSeed).toFloat() * scaleFactor
            val y2 = sin(angle) * scaleFactor


            catmullRom.addVertex(point.x, point.y)
            catmullRom.addVertex(x1 + point.x, point.y + y1)
            catmullRom.addVertex(x2 + point.x, point.y + y2.toFloat())
            angle += speed
        }

        catmullRom.build()

        svg.append(catmullRom.toSVG("#550000", 0.6f))

        println(svg.build())
    }
}