package frc.team4096.engine.motion.classicControl

import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.robot.misc.MiscConsts
import kotlin.math.abs

/**
 * Simple, synchronous PID controller.
 * Can use fixed feedforward.
 *
 * @param pidfVals PID gains
 */
class PIDFController(val pidfVals: PIDVAVals) {
    var error = 0.0
        private set
    private var lastError = 0.0
    private var integral = 0.0
    private var derivative = 0.0

    var setpoint = 0.0
    var tolerance = 0.0

    val onTarget
        get() = abs(error) <= abs(tolerance)

    /**
     * Resets controller to defaults.
     * Resets error, accumulator, setpoint, and tolerance.
     */
    fun reset() {
        error = 0.0
        lastError = 0.0
        integral = 0.0
        derivative = 0.0
        setpoint = 0.0
        tolerance = 0.0
    }

    /**
     * Calculate the output.
     *
     * @return Output of controller
     */
    fun calculate(sensorPos: Double): Double {
        error = setpoint - sensorPos
        integral += error * MiscConsts.K_DT
        derivative = (error - lastError) / MiscConsts.K_DT

        val output =
                pidfVals.kP * error +
                pidfVals.kI * integral +
                pidfVals.kD * derivative +
                pidfVals.kF

        lastError = error

        return output
    }
}
