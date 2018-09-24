package frc.team4096.engine.motion.classicControl

import frc.team4096.engine.async.AsyncLooper
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.engine.motion.PVAJData
import frc.team4096.engine.motion.profiles.MotionProfile

/**
 * Custom asynchronous PIDVA controller.
 * Used to follow Profiles.
 *
 * @param pidvaVals P, I, D, and F values
 * @param profile Profile to follow
 * @constructor Constructor
 */
class PIDVAController(
        val pidvaVals: PIDVAVals,
        val profile: MotionProfile,
        val freq: Double = 100.0
) {
    private var error = 0.0
    private var lastError = 0.0
    private var integral = 0.0
    private var derivative = 0.0
    private var output = 0.0

    private var sensorPos = 0.0
    private var pvajData = PVAJData()
    private val dt = 1 / freq

    private val looper = AsyncLooper(freq, true) { run() }
    var isEnabled
        get() = looper.job.isActive
        set(state) {
            looper.apply { if (state) start() else stop() }
        }

    /**
     * Enables controller and launches coroutine.
     */
    fun enable() {
        profile.reset()
        isEnabled = true
    }

    private fun calculate() {
        error = pvajData.pos - sensorPos

        integral += error * dt
        derivative = (error - lastError) / dt

        output =
                pidvaVals.kP * error +
                pidvaVals.kI * integral +
                pidvaVals.kD * derivative +
                pidvaVals.kV * pvajData.vel +
                pidvaVals.kA * pvajData.accel +
                pidvaVals.kF

        lastError = error
    }

    private fun run() {
        sensorPos = profile.source()
        pvajData = profile.follow(sensorPos)
        calculate()
        profile.sink(output)
    }

    /**
     * Disables controller.
     */
    fun disable() {
        isEnabled = false

        // Reset variables
        error = 0.0
        lastError = 0.0
        integral = 0.0
        derivative = 0.0
        output = 0.0
        sensorPos = 0.0
        pvajData = PVAJData()
    }
}

/*
class PIDVAController2(
        val pidvaVals: PIDVAVals,
        val profile: MotionProfile,
        val freq: Double = 100.0
) : AsyncPIDFController(
        pidvaVals,
        { pidvaVals.kV * profile. + pidvaVals * profile.source},

) {

}
*/