package oppen.shapes

abstract class OppenShape {
    abstract fun toSVG(): String
    abstract fun toSVG(colour: String, opacity: Float = 0.20f): String
}