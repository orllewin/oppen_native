package oppen.shapes.primitives

import oppen.shapes.OppenShape

class Rectangle(val x: Int, val y: Int, val width: Int, val height: Int): OppenShape() {

    override fun toSVG(): String {
       //todo
        return ""
    }

    override fun toSVG(colour: String, opacity: Float): String {
        //todo
        return ""
    }
}