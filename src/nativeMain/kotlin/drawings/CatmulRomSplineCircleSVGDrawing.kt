package drawings

import oppen.SVGDrawing
import oppen.SVG
import oppen.shapes.primitives.CatmullRomSpline
import oppen.stdlib.Random
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@ExperimentalUnsignedTypes
class CatmulRomSplineCircleSVGDrawing: SVGDrawing() {

    override val label: String = "Catmull-Rom Spline Circle Drawing"

    private val width = 900
    private val height = 900
    private val iterations = 1000
    private val speed = 0.05f
    private var xSeed = 0f
    private var ySeed = 0f
    private val stroke = "#666666"
    private val opacity = 0.8f
    private val background = "#ffffff"

    private val svg = SVG(width, height, background)
    private val catmullRom = CatmullRomSpline(CatmullRomSpline.Type.CHORDAL, 30)

    private var angle = 0.0
    private var scaleFactor = 65.0f

    init {
        xSeed = Random.random(2f, 5f)
        ySeed = Random.random(2f)
    }

    override fun initialise() {

    }

    override fun draw() {
        repeat(iterations) { index ->
            val c = cos(angle / xSeed).toFloat()
            val s = sin(angle * ySeed).toFloat()

            val x1 = c * scaleFactor
            val y1 = (height / 2f) + s * scaleFactor

            val x2 = cos(angle / xSeed).toFloat() * scaleFactor
            val y2 = (height / 2) + sin(angle) * scaleFactor

            val xOrigin = (width/2) + 200 * sin((index) * PI / 180).toFloat()
            val yOrigin = 20 + 200 * cos((index) * PI / 180).toFloat()

            val yAnchor = (height / 2) + 40 * sin((index) * PI / 180)
            val xAnchor = 40 * sin((index) * PI / 180)

            catmullRom.addVertex(xOrigin + xAnchor.toFloat(), yOrigin + yAnchor.toFloat())
            catmullRom.addVertex(x1 + xOrigin, yOrigin + y1)
            catmullRom.addVertex(x2 + xOrigin, yOrigin + y2.toFloat())
            angle += speed
        }

        catmullRom.build()

        svg.append(catmullRom.toSVG(stroke, opacity))

        println(svg.build())
    }
}