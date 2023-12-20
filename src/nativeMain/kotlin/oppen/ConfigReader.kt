package oppen

import oppen.stdlib.FileIO

//todo - pre parse in init into String,String hashmap to make lookups quicker
class ConfigReader(private val file: String) {

    private val fileIO = FileIO()
    lateinit var lines: List<String>

    fun load(): ConfigReader {
        lines = fileIO.readLines(file)
        return this
    }

    fun exists(): Boolean = fileIO.exists(file)
    fun make() = fileIO.write(file, "")

    fun get(key: String): String? {
        val line = lines.find { line ->
            line.toLowerCase().startsWith("${key.toLowerCase()}:")
        }
        return line?.substring(key.length + 1)?.trim()
    }

    fun getInt(key: String): Int? {
        val line = lines.find { line ->
            line.toLowerCase().startsWith("${key.toLowerCase()}:")
        }
        return line?.substring(key.length + 1)?.trim()?.toIntOrNull()
    }

    fun getFloat(key: String): Float? {
        val line = lines.find { line ->
            line.toLowerCase().startsWith("${key.toLowerCase()}:")
        }
        return line?.substring(key.length + 1)?.trim()?.toFloatOrNull()
    }
}