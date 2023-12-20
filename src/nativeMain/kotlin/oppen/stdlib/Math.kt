package oppen.stdlib

import kotlin.math.PI

typealias Degree = Double
typealias Radian = Double

object Math {
    const val TAU = (PI * 2).toFloat()

    fun Degree.rad(): Radian = this / 180 * PI
    fun Radian.deg(): Degree = this * 180 / PI

    fun randomDegree(): Degree {
        return Random.random(0.0, 360.0)
    }

    fun map(value: Float, start1: Float, stop1: Float, start2: Float, stop2: Float): Float{
        return start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1))
    }

    private fun lerp(start: Float, end: Float, amount: Float): Float {
        return start * (1-amount) + end * amount
    }

}

