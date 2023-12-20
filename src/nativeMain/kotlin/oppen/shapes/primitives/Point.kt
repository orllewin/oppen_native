package oppen.shapes.primitives

import oppen.shapes.OppenShape
import oppen.stdlib.Degree
import oppen.stdlib.Math.rad
import kotlin.math.cos
import kotlin.math.floor
import kotlin.math.sin

data class Point(var x: Float, var y: Float): OppenShape(){

    constructor(x: Number, y: Number): this(x.toFloat(), y.toFloat())

    override fun toSVG(): String {
        return toSVG("black")
    }

    override fun toSVG(colour: String, opacity: Float): String {
        return "<ellipse cx=\"${floor(x)}\" cy=\"${floor(y)}\" rx=\"1\" ry=\"1\" style=\"stroke:$colour;opacity:$opacity;\" />\n"
    }

    operator fun plus(point: Point): Point {
        return Point(this.x + point.x, this.y + point.y)
    }

    fun plot(angle: Degree, distance: Float): Point {
        return Point(this.x + (distance * cos(angle.rad())), this.y + (distance * sin(angle.rad())))
    }

    companion object{
        fun midPoint(a: Point, b: Point): Point{
            return Point((a.x + b.x) / 2, (a.y + b.y) / 2)
        }
    }
}
