package drawings

import oppen.ConfigReader
import oppen.SVGDrawing
import oppen.SVG
import oppen.shapes.primitives.CatmullRomSpline
import oppen.stdlib.Random
import kotlin.math.cos
import kotlin.math.sin

@ExperimentalUnsignedTypes
class CatmulRomSplineSVGDrawing: SVGDrawing() {

    override val label: String = "Catmull-Rom Spline Test Drawing"

    private val configFile = "catmul_rom_test.txt"
    private val config = ConfigReader(configFile).load()
    private val width = config.getInt("width") ?: 1200
    private val height = config.getInt("height") ?: 1200
    private var xOrigin = config.getFloat("xorigin") ?: 0f
    private val startIndex = config.getFloat("startindex") ?: 0f
    private val iterations = config.getInt("iterations") ?: 700
    private val lineIncrement = config.getFloat("line_increment") ?: 1.45f
    private val speed = config.getFloat("speed") ?: 0.05f
    private val xSeed = config.getFloat("xseed") ?: Random.random(6f, 15f)
    private val ySeed = config.getFloat("yseed") ?: Random.random(5f)
    private val stroke = config.get("stroke") ?: "#666666"
    private val opacity = config.getFloat("opacity") ?: 0.2f
    private val background = config.get("background") ?: "#ffffff"

    private val svg = SVG(width, height, background)
    private val catmullRom = CatmullRomSpline(CatmullRomSpline.Type.CHORDAL, 30)

    private var angle = 0.0

    override fun initialise() {

    }

    override fun draw() {
        repeat(iterations) { index ->
            when {
                index < startIndex -> {
                    //skip first few to remove drawing artifact
                }
                else -> {
                    val c = cos(angle / xSeed).toFloat()
                    val s = sin(angle * ySeed).toFloat()

                    val x1 = c * 100f
                    val y1 = (height / 2f) + s * 200f

                    val x2 = cos(angle / xSeed).toFloat() * 100f
                    val y2 = (height / 2) + sin(angle) * 200

                    val yAnchor = (height / 2) + 40 * sin((index) * 3.14 / 180)
                    val xAnchor = 25 * sin((index) * 3.14 / 180)

                    catmullRom.addVertex(xOrigin + xAnchor.toFloat(), yAnchor.toFloat())
                    catmullRom.addVertex(x1 + xOrigin, y1)
                    catmullRom.addVertex(x2 + xOrigin, y2.toFloat())
                    xOrigin += lineIncrement
                    angle += speed
                }
            }

        }

        catmullRom.build()

        svg.append(catmullRom.toSVG(stroke, opacity))

        println(svg.build())
    }
}