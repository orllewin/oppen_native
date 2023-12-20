package oppen.stdlib

import kotlinx.cinterop.*
import platform.posix.*

const val MODE_READ = "r"
const val MODE_WRITE = "w"
const val NEW_LINE = "\n"

/**
 *
 * Derived from https://www.nequalsonelifestyle.com/2020/11/16/kotlin-native-file-io/
 * References
 *  - https://www.tutorialspoint.com/c_standard_library/c_function_fopen.htm
 *
 */
class FileIO {

    fun exists(filePath: String): Boolean{
        val file = fopen(filePath, MODE_READ)
        val exists = file != null
        fclose(file)
        return exists
    }

    fun read(filePath: String): String {
        val returnBuffer = StringBuilder()
        val file = fopen(filePath, MODE_READ) ?: throw IllegalArgumentException("Cannot open file $filePath")

        try {
            memScoped {
                val readBufferLength = 64 * 1024
                val buffer = allocArray<ByteVar>(readBufferLength)
                var line = fgets(buffer, readBufferLength, file)?.toKString()
                while (line != null) {
                    returnBuffer.append(line)
                    line = fgets(buffer, readBufferLength, file)?.toKString()
                }
            }
        } finally {
            fclose(file)
        }

        return returnBuffer.toString()
    }

    fun readLines(filePath: String): List<String>{
        return read(filePath).split(NEW_LINE)
    }

    fun write(filePath:String, text:String) {
        val file = fopen(filePath, MODE_WRITE) ?: throw IllegalArgumentException("Cannot open file $filePath")
        try {
            memScoped {
                if(fputs(text, file) == EOF) throw Error("File write error")
            }
        } finally {
            fclose(file)
        }
    }

    fun write(filePath:String, lines:List<String>) {
        val file = fopen(filePath, MODE_WRITE) ?: throw IllegalArgumentException("Cannot open output file $filePath")
        try {
            memScoped {
                lines.forEach { line ->
                    if(fputs("$line$NEW_LINE", file) == EOF) throw Error("File write error")
                }
            }
        } finally {
                fclose(file)
        }
    }
}