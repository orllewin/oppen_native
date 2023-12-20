package oppen.shapes.primitives

import oppen.shapes.OppenShape

class Circle(val x: Int, val y: Int, val r: Int): OppenShape() {

    constructor(p: Point, r: Int): this(p.x.toInt(), p.y.toInt(), r)

    override fun toSVG(): String {
        //todo
        return ""
    }

    override fun toSVG(colour: String, opacity: Float): String {
        //todo
        return ""
    }
}