package oppen.stdlib

import oppen.shapes.primitives.Point
import kotlin.math.sqrt

class Vector(var x: Float, var y: Float) {

    constructor(x: Number, y: Number) : this(x.toFloat(), y.toFloat())
    constructor(point: Point) : this(point.x, point.y)

    companion object {
        fun dot(v1: Vector, v2: Vector): Float {
            return v1.x * v2.x + v1.y * v2.y
        }

        fun randomDirection(): Vector {
            val direction = Vector(Random.random(-1f, 1f), Random.random(-1f, 1f))
            direction.normalise()
            return direction
        }
    }

    fun normalise(){
        val magnitude = magnitude()
        if(magnitude > 0){
            this.x = x/magnitude
            this.y = y/magnitude
        }
    }

    fun distance(other: Vector): Float{
        val dx = x - other.x
        val dy = y - other.y
        return sqrt(dx * dx + dy * dy)
    }

    fun direction(other: Vector): Vector {
        val direction = Vector(other.x - x, other.y - y)
        direction.normalise()
        return direction
    }

    fun perpendicular(): Vector {
        val pX = -y
        val pY = x
        return Vector(pX, pY)
    }

    fun limit(max: Float){
        when {
            magnitudeSquared() > max * max -> {
                normalise()
                x *= max
                y *= max
            }
        }
    }

    fun magnitude(): Float = sqrt(x * x + y * y)
    fun dot(other: Vector): Float = x * other.x + y * other.y
    fun magnitudeSquared(): Float = x * x + y * y
    fun point(): Point = Point(x.toInt(), y.toInt())
    fun clone(): Vector = Vector(x, y)

    operator fun plus(vector: Vector): Vector {
        return Vector(this.x + vector.x, this.y + vector.y)
    }

    operator fun minus(vector: Vector): Vector {
        return Vector(this.x - vector.x, this.y - vector.y)
    }

    operator fun times(value: Number): Vector {
        return Vector(this.x * value.toFloat(), this.y * value.toFloat())
    }

    operator fun div(value: Number): Vector {
        return Vector(this.x / value.toFloat(), this.y / value.toFloat())
    }

}