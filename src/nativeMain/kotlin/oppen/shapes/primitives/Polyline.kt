package oppen.shapes.primitives

import oppen.shapes.OppenShape

class Polyline: OppenShape() {

    private val vertices = arrayListOf<Point>()

    fun addVertex(point: Point){
        vertices.add(point)
    }

    fun addVertex(x: Float, y: Float){
        vertices.add(Point(x, y))
    }

    override fun toSVG(): String {
        return toSVG("black")
    }

    override fun toSVG(colour: String, opacity: Float): String {
        val sb = StringBuilder()
        sb.append("<polyline points=\"")
        for (i in 0 until vertices.size) {
            sb.append("${vertices[i].x},${vertices[i].y} ")
        }
        sb.dropLast(1)
        sb.append("\" style=\"stroke:$colour;fill:none;opacity:$opacity;\" />")
        return sb.toString()
    }
}