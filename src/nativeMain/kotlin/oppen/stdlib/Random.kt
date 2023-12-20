package oppen.stdlib

import kotlin.math.floor
import kotlin.random.Random

object Random {

    fun random(): Float{
        return Random.Default.nextFloat()
    }

    fun random(max: Float): Float {
        return random() * max
    }

    fun random(min: Float, max: Float): Float {
        return (random() * (max - min)) + min
    }

    fun random(max: Int): Int {
        return floor(random() * max).toInt()
    }

    fun random(min: Int, max: Int): Int {
        return floor((random() * (max - min)) + min).toInt()
    }

    fun random(min: Double, max: Double): Double {
        return floor((random() * (max - min)) + min)
    }
}