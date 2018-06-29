package frc.team4096.engine.motion.util

import edu.wpi.first.wpilibj.PIDSourceType
import frc.team4096.robot.util.MiscConsts
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class PIDFController(
	val pidfVals: PIDFVals,
	var setpoint: Double,
	val pidSourceFun: () -> Double,
	val pidSinkFun: (Double) -> Unit,
	val pidSourceType: PIDSourceType
) {
	var error = 0.0
	var lastError = 0.0
	var integral = 0.0
	var derivative = 0.0
	var isEnabled = false

	fun enable() {
		isEnabled = true
		launch {
			while (isEnabled) {
				error = setpoint - pidSourceFun()
				integral += (error * MiscConsts.K_DT)
				derivative = (error - lastError) / MiscConsts.K_DT

				pidSinkFun(
					pidfVals.kP * error +
						pidfVals.kI * integral +
						pidfVals.kD * derivative +
						pidfVals.kF
				)

				lastError = error

				// Wait 20ms (50Hz)
				delay((MiscConsts.K_DT * 1000).toInt())
			}
		}
	}

	fun disable() { isEnabled = false }
}