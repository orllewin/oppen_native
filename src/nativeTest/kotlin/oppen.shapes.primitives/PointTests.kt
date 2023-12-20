package oppen.shapes.primitives

import kotlin.test.Test
import kotlin.test.assertTrue

class PointTests {

    @Test
    fun operatorAddition(){
        val a = Point(1, 1)
        val b = Point(2, 2)

        val c = a + b

        assertTrue("a + b is correct"){
            c.x == 3f && c.y == 3f
        }
    }
}