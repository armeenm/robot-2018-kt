package frc.team4096.engine.logging

import java.util.UUID.randomUUID

object Logger {
    val uuid = randomUUID()?.toString() ?: "UUID_GEN_FAIL"

    val folderPath = "/home/lvuser/logs"
    val errorLogPath = "$folderPath/errorLog-$uuid.log"
    val dataLogPath = "$folderPath/datalog-$uuid.log"

    var flushFreq = 1.0

}

enum class LogLevel { INFO, WARNING, ERROR, FATAL, DEBUG }
