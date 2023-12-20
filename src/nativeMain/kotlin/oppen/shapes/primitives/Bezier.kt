package oppen.shapes.primitives

import oppen.shapes.OppenShape

class Bezier(
    p1: Point,
    p2: Point,
    p3: Point,
    p4: Point,
    private val detail: Int): OppenShape() {

    val points = List(detail + 1) { Point(0, 0) }

    init {
        for (i in 0..detail) {
            val t = i.toDouble() / detail
            val u = 1.0 - t
            val a = u * u * u
            val b = 3.0 * t * u * u
            val c = 3.0 * t * t * u
            val d = t * t * t
            points[i].x = (a * p1.x + b * p2.x + c * p3.x + d * p4.x).toFloat()
            points[i].y = (a * p1.y + b * p2.y + c * p3.y + d * p4.y).toFloat()
        }
    }

    override fun toSVG(): String {
        return toSVG("black")
    }

    override fun toSVG(colour: String, opacity: Float): String {
        val sb = StringBuilder()
        sb.append("<polyline points=\"")
        for (i in 0 until detail) {
            val j = i + 1
            sb.append("${points[i].x},${points[i].y} ${points[j].x},${points[j].y} ")
        }
        sb.dropLast(1)
        sb.append("\" style=\"stroke:$colour;fill:none;opacity:$opacity;\" />")
        return sb.toString()
    }
}