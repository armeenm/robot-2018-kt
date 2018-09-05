package frc.team4096.engine.logging

import java.util.UUID.randomUUID
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {
    val uuid = randomUUID()?.toString() ?: "UUID_GEN_FAIL"

    val folderPath = "/home/lvuser/logs"
    val errorLogPath = "$folderPath/errorLog-$uuid.log"
    val dataLogPath = "$folderPath/datalog-$uuid.log"

    var flushFreq = 1.0

    //TODO add buffer (array)

    fun log(level: LogLevel, log: String) {
        val file = File(filePathForLevel(level))
        val isNewFileCreated: Boolean = file.createNewFile()

        file.printWriter().use { out ->
            val time = LocalDateTime.now()
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
            val formatted = time.format(formatter)
            out.println("$formatted, $level, $log")
        }
    }

    fun filePathForLevel(level: LogLevel) = when (level) {
        LogLevel.INFO, LogLevel.WARNING, LogLevel.DEBUG -> dataLogPath
        LogLevel.ERROR, LogLevel.FATAL -> errorLogPath
    }
}

enum class LogLevel { INFO, WARNING, ERROR, FATAL, DEBUG }
