package oppen

import cnames.structs.SDL_Renderer
import cnames.structs.SDL_Window
import kotlinx.cinterop.*
import oppen.sdl.SDLColour
import oppen.shapes.OppenShape
import oppen.shapes.primitives.Circle
import oppen.shapes.primitives.Line
import oppen.shapes.primitives.Point
import oppen.shapes.primitives.Rectangle
import sdl.*
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.native.concurrent.AtomicInt

const val UNKNOWN = "Unknown"

typealias Drawing = SDL

@ExperimentalUnsignedTypes
open class SDL(
    val width: Int,
    val height: Int) {
    private var looping = true
    private val window: CPointer<SDL_Window>
    private val renderer: CPointer<SDL_Renderer>
    private val platform: String
    private var displayWidth: Int = 0
    private var displayHeight: Int = 0

    private val backgroundColour = SDLColour(0)
    private val strokeColour = SDLColour(255)
    private var strokeAlpha = 255
    private val fillColour = SDLColour(255)
    private var fillAlpha = 255

    private var drawLock = AtomicInt(1)

    init {
        if (SDL_Init(SDL_INIT_EVERYTHING) != 0) {
            println("SDL_Init Error: ${sdlError()}")
            throw Error()
        }

        platform = SDL_GetPlatform()?.toKString() ?: UNKNOWN

        memScoped {
            val displayMode = alloc<SDL_DisplayMode>()
            if (SDL_GetCurrentDisplayMode(0, displayMode.ptr.reinterpret()) != 0) {
                println("SDL_GetCurrentDisplayMode Error: ${sdlError()}")
                SDL_Quit()
                throw Error()
            }

            displayWidth = displayMode.w
            displayHeight = displayMode.h
        }

        val windowX = (displayWidth - width) / 2
        val windowY = (displayHeight - height) / 2

        val window = SDL_CreateWindow("OppenNative", windowX, windowY, width, height, SDL_WINDOW_SHOWN)
        if (window == null) {
            println("SDL_CreateWindow Error: ${sdlError()}")
            SDL_Quit()
            throw Error()
        }
        this.window = window

        val renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED or SDL_RENDERER_PRESENTVSYNC)
        if (renderer == null) {
            SDL_DestroyWindow(window)
            println("SDL_CreateRenderer Error: ${sdlError()}")
            SDL_Quit()
            throw Error()
        }

        SDL_SetRenderDrawBlendMode(renderer, SDL_BLENDMODE_BLEND)
        SDL_SetHint( SDL_HINT_RENDER_SCALE_QUALITY, "1" )
        this.renderer = renderer

        SDL_PumpEvents()
        SDL_SetWindowSize(window, width, height)


        startLooping()
    }

    private fun startLooping() {

        initialise()

        while (looping) {
            sleep(1000 / 60) // Refresh rate - 60 frames per second.
            readInput()
            setRenderColour(backgroundColour)
            SDL_RenderClear(renderer)
            setRenderColour(strokeColour, strokeAlpha)
            if(drawLock.value == 0) draw()
            SDL_RenderPresent(renderer)
        }
    }

    private fun setRenderColour(colour: SDLColour) = setRenderColour(colour, 255)
    private fun setRenderColour(colour: SDLColour, opacity: Int){
        SDL_SetRenderDrawColor(renderer, colour.r, colour.g, colour.b, opacity.toUByte())
    }

    open fun initialise(){
        unlock()
    }

    open fun draw(){

    }

    open fun key(char: Char?){

    }

    fun lock(){
        drawLock.value = 1
    }

    fun unlock(){
        drawLock.value = 0
    }

    private fun readInput() {
        memScoped {
            val event = alloc<SDL_Event>()
            while (SDL_PollEvent(event.ptr.reinterpret()) != 0) {
                when (event.type) {
                    SDL_QUIT -> close()
                    SDL_KEYDOWN -> {
                        val keyboardEvent = event.ptr.reinterpret<SDL_KeyboardEvent>().pointed
                        when (keyboardEvent.keysym.scancode) {
                            SDL_SCANCODE_ESCAPE -> close()
                            else -> {
                                val char = SDL_GetKeyName(keyboardEvent.keysym.sym)?.toKString()?.first()
                                key(char)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun sdlError() = SDL_GetError()?.toKString() ?: UNKNOWN
    fun sleep(ms: Int) = SDL_Delay(ms.toUInt())


    fun drawShape(shape: OppenShape){
        when(shape){
            is Line -> line(shape)
            is Rectangle -> rect(shape)
            is Circle -> circle(shape)
        }
    }

    fun line(a: Point, b: Point) = line(Line(a, b))
    fun line(x1: Int, y1: Int, x2: Int, y2: Int) = line(Line(x1, y1, x2, y2))
    fun line(line: Line){
        SDL_RenderDrawLine(renderer, line.x1, line.y1, line.x2, line.y2)
    }

    fun rect(rect: Rectangle){
        setRenderColour(fillColour, fillAlpha)
            val rectangle = cValue<SDL_Rect> {
                x = rect.x
                y = rect.y
                w = rect.width
                h = rect.height
            }
        SDL_RenderFillRect(renderer, rectangle)
        setRenderColour(strokeColour, strokeAlpha)
    }

    fun circle(c: Circle) = circle(c.x, c.y, c.r)
    fun circle(x: Int, y: Int, r: Int){
        setRenderColour(strokeColour, strokeAlpha)
        repeat(r+1){ dy ->
            val dx: Double = floor(sqrt(2.0 * r * dy - dy * dy))
            SDL_RenderDrawLine(renderer, x - dx.toInt(), y + dy - r, x + dx.toInt(), y + dy - r)
            if(dy < r) SDL_RenderDrawLine(renderer, x - dx.toInt(), y - dy + r, x + dx.toInt(), y - dy + r)
        }
    }


    /**
     * Colour Assignments
     */
    fun stroke(v: Int) = strokeColour.from(v)
    fun stroke(v: Int, a: Int){
        strokeColour.from(v)
        strokeAlpha = a
    }
    fun stroke(r: Int, g: Int, b: Int) = strokeColour.from(r, g, b)
    fun stroke(r: Int, g: Int, b: Int, a: Int) {
        strokeColour.from(r, g, b)
        strokeAlpha = a
    }
    fun stroke(colour: String) = strokeColour.from(colour)
    fun strokeOpacity(opacity: Int) {
        this.strokeAlpha = opacity
    }
    fun fill(v: Int) = fillColour.from(v)
    fun fill(v: Int, a: Int) {
        fillColour.from(v)
        fillAlpha = a
    }
    fun fill(r: Int, g: Int, b: Int) = fillColour.from(r, g, b)
    fun fill(r: Int, g: Int, b: Int, a: Int) {
        fillColour.from(r, g, b)
        fillAlpha = a
    }
    fun fill(colour: String) = fillColour.from(colour)
    fun fillOpacity(opacity: Int) {
        this.fillAlpha = opacity
    }
    fun background(v: Int) = backgroundColour.from(v)
    fun background(colour: String) = backgroundColour.from(colour)
    fun background(r: Int, g: Int, b: Int){
        backgroundColour.r(r)
        backgroundColour.g(g)
        backgroundColour.b(b)
    }

    fun exit() = close()

    private fun close(){
        looping = false
        SDL_DestroyRenderer(renderer)
        SDL_DestroyWindow(window)
        SDL_Quit()
    }
}