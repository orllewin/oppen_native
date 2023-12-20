import drawings.For_S
import drawings.GrowthSDL
import drawings.SDLDrawingTest
import platform.posix.system

@ExperimentalUnsignedTypes
fun main() {
    system("clear")
    //println("\u000c")
    println("")
    println("888b    888        d8888 88888888888 8888888 888     888 8888888888")
    println("8888b   888       d88888     888       888   888     888 888")
    println("88888b  888      d88P888     888       888   888     888 888")
    println("888Y88b 888     d88P 888     888       888   Y88b   d88P 8888888")
    println("888 Y88b888    d88P  888     888       888    Y88b d88P  888")
    println("888  Y88888   d88P   888     888       888     Y88o88P   888")
    println("888   Y8888  d8888888888     888       888      Y888P    888")
    println("888    Y888 d88P     888     888     8888888     Y8P     8888888888")
    println("")
    println("Select drawing:")
    println("1 - Animation Test")
    println("2 - For S")
    println("3 - Growth")
    println("------------------")
    println("Q - Quit")
    val input = readLine()

    if (input?.toLowerCase() == "q") return

    val choice = input?.toIntOrNull()
    if (choice == null) {
        println("...")
        GrowthSDL()
        return
    }

    when (choice) {
        1 -> {
            println("Starting Animation Test")
            SDLDrawingTest()
        }
        2 -> {
            println("Starting For S")
            For_S()
        }
        3 -> {
            println("Starting Growth")
            GrowthSDL()
        }
    }
}

