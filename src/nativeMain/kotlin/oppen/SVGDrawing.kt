package oppen

@ExperimentalUnsignedTypes
abstract class SVGDrawing {

    init {
        this.initialise()
        this.draw()
    }

    abstract val label: String
    abstract fun initialise()
    abstract fun draw()
}