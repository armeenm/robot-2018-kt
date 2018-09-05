package frc.team4096.engine.logging

import frc.team4096.engine.dsp.DiskBuffer
import java.util.UUID.randomUUID
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object Logger {
    val uuid = randomUUID()?.toString() ?: "UUID_GEN_FAIL"

    val folderPath = "/home/lvuser/logs"
    val errorLogPath = "$folderPath/errorLog-$uuid.log"
    val dataLogPath = "$folderPath/datalog-$uuid.log"

    lateinit var errorBuffer :DiskBuffer
    lateinit var dataBuffer :DiskBuffer

    val bufferSize = 100
    //val flushFreq = 1.0       //TODO add later so we dont have to actually write 100 files of logs

    //TODO incorperate co-routines

    fun log(level: LogLevel, log: String) {
        initializeBuffer(level)
        val buffer = when(level) {
            LogLevel.INFO, LogLevel.WARNING, LogLevel.DEBUG -> dataBuffer
            LogLevel.ERROR, LogLevel.FATAL -> errorBuffer
        }

        val time = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = time.format(formatter)

        buffer.add("$formatted, $level, $log")
    }

    //create a buffer for data log file if one does not exist
    private fun initializeDataBuffer() {
        if(this::dataBuffer.isInitialized) return

        val file = File(dataLogPath)
        file.createNewFile()

        dataBuffer = DiskBuffer(bufferSize, file)
    }

    //create a buffer for error log file if one does not exist
    private fun initializeErrorBuffer() {
        if(this::errorBuffer.isInitialized) return

        val file = File(errorLogPath)
        file.createNewFile()

        errorBuffer = DiskBuffer(bufferSize, file)
    }

    //create a buffer for a log file if one does not exist
    private fun initializeBuffer(level :LogLevel) = when(level) {
        LogLevel.INFO, LogLevel.WARNING, LogLevel.DEBUG -> this.initializeDataBuffer()
        LogLevel.ERROR, LogLevel.FATAL -> this.initializeErrorBuffer()
    }
}

enum class LogLevel { INFO, WARNING, ERROR, FATAL, DEBUG }
