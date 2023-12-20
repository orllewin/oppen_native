package oppen.sdl

import sdl.Uint8

@ExperimentalUnsignedTypes
class SDLColour(var r: Uint8 = 0u, var g: Uint8 = 0u, var b: Uint8 = 0u){

    constructor(r: Int, b: Int, g: Int): this(r.toUByte(), g.toUByte(), b.toUByte())
    constructor(v: Int): this(v.toUByte(), v.toUByte(), v.toUByte())

    /**
     *
     * @param colour - String in format #rrggbb, eg. "#ff00cc"
     */
    constructor(colour: String): this(
        colour.substring(1, 3).toInt(16).toUByte(),
        colour.substring(3, 5).toInt(16).toUByte(),
        colour.substring(5, 7).toInt(16).toUByte()
    )

    fun r(red: Int){
        r = red.toUByte()
    }

    fun g(green: Int){
        g = green.toUByte()
    }

    fun b(blue: Int){
        b = blue.toUByte()
    }

    fun from(v: Int){
        r = v.toUByte()
        g = v.toUByte()
        b = v.toUByte()
    }

    fun from(r: Int, g: Int, b: Int){
        this.r = r.toUByte()
        this.g = g.toUByte()
        this.b = b.toUByte()
    }

    fun from(colour: String) {
        r = colour.substring(1, 3).toInt(16).toUByte()
        g = colour.substring(3, 5).toInt(16).toUByte()
        b = colour.substring(5, 7).toInt(16).toUByte()
    }
}