package oppen.shapes.primitives

import oppen.shapes.OppenShape

class Line(var x1: Int, var y1: Int, var x2: Int, var y2: Int): OppenShape() {

    constructor(a: Point, b: Point): this(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt())

    override fun toSVG(): String {
        return toSVG("black")
    }

    override fun toSVG(colour: String, opacity: Float): String {
        return "<line x1=\"$x1\" y1=\"$y1\" x2=\"$x2\" y2=\"$y2\" style=\"stroke:$colour;stroke-width:1;opacity:$opacity;\" />"
    }
}