package oppen

import oppen.shapes.OppenShape

const val NEWLINE = "\n"

class SVG(width: Int, height: Int, backgroundColour: String = "#ffffff"){

    private val sb = StringBuilder()

    init {
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n")
        sb.append("<svg width=\"$width\" height=\"$height\" viewBox=\"0 0 $width $height\" style=\"background-color:$backgroundColour\" xmlns=\"http://www.w3.org/2000/svg\">\n")
    }

    fun append(svg: String){
        sb.append("$svg$NEWLINE")
    }

    fun append(shape: OppenShape){
        sb.append("${shape.toSVG()}$NEWLINE")
    }

    fun build(): String {
        sb.append("</svg>")
        return sb.toString()
    }
}