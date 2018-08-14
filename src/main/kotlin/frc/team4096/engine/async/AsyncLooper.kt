package frc.team4096.engine.async

import edu.wpi.first.wpilibj.Timer
import kotlinx.coroutines.experimental.*

/**
 * Asynchronous coroutine-based looper.
 * Based on code from 2898.
 */
class AsyncLooper(val freq: Double, var logging: Boolean, loopFun: suspend () -> Unit = {}) {
    var onStart: () -> Unit = {}
    var onLoop: suspend () -> Unit = loopFun
    var onStop: () -> Unit = {}

    var job = init()

    var msPeriod = 1000 / freq

    private fun init(): Job {
        return launch(CommonPool, CoroutineStart.LAZY) {
            while (isActive) {
                val startTime = Timer.getFPGATimestamp()
                onLoop()
                val deltaTime = Timer.getFPGATimestamp() - startTime
                if (deltaTime < msPeriod) delay((msPeriod - deltaTime).toLong())
                else if (logging) logCantKeepUp(deltaTime)
            }
        }
    }

    fun start() {
        onStart()
        job.start()
    }

    fun stop() {
        job.cancel()
        onStop()
    }

    private fun logCantKeepUp(lastTime: Double) {
        println("AsyncLooper cannot keep up! Running at $freq Hz. and last loop time was ${lastTime * 1000} ms.")
    }
}