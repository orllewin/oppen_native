package oppen.stdlib

import kotlin.test.Test
import kotlin.test.assertTrue

class RandomTests {

    @Test
    fun randomFloatMaxTest(){
        repeat(100) {
            assertTrue("Random in range max 3") {
                val rnd = Random.random(3f)
                println("rnd: $rnd")
                rnd >= 0 && rnd < 3
            }
        }
    }

    @Test
    fun randomFloatRangeTest(){
        repeat(100) {
            assertTrue("Random in range max 3") {
                val rnd = Random.random(1f, 3f)
                println("rnd: $rnd")
                rnd >= 1 && rnd < 3
            }
        }
    }

    @Test
    fun randomIntTest(){
        repeat(100) {
            assertTrue("Random in range max 3") {
                val rnd = Random.random(3)
                println("rnd: $rnd")
                rnd in 0..2
            }
        }
    }

    @Test
    fun randomIntRangeTest(){
        repeat(100) {
            assertTrue("Random in range max 3") {
                val rnd = Random.random(4, 10)
                println("rnd: $rnd")
                rnd in 4 until 10
            }
        }
    }
}