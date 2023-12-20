package drawings

/*
class SDLTest: SDLDrawing(600, 600) {

    override val label: String = "SDL test"

    private val sdl: SDL = SDL(600, 600)

    private val width = 600
    private val height = 600
    private val background = "#ffffff"

    private var reduction = 1.5f

    private val lines = arrayListOf<Line>()

    override fun initialise() {

    }

    override fun draw() {

        val c = Point(width/2, height/2)
        val length = 100f

        reduction = Random.random(1.2f, 1.9f)

        repeat(Random.random(3, 5)){
            grow(c, length, Math.randomDegree())
        }

        sdl.drawFrame(lines)
    }

    private fun grow(p: Point, length: Float, angle: Degree) {
        if (length < 3 || lines.size > 8000) return

        val p2 = p.plot(angle, length)
        lines.add(Line(p, p2))

        val i = Random.random(2, 4)
        repeat(i) {
            val a: Radian = Random.random(-12f, 12f).toDouble()
            grow(p2, length / reduction, a.deg())
        }
    }

}

 */