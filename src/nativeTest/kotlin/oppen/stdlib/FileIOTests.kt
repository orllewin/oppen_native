package oppen.stdlib

import kotlin.test.Test
import kotlin.test.assertTrue

class FileIOTests {

    @Test
    fun ioReadWrite(){
        val io = FileIO()

        val filePath = "src/nativeTest/kotlin/oppen/stdlib/io_text.txt"
        val fileContent = "OppenNative"

        io.write(filePath, fileContent)

        assertTrue("io_text.txt exists"){
            val content = io.read(filePath)
            content == fileContent
        }
    }
}