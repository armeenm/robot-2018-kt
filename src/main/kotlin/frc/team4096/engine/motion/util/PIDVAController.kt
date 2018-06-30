package frc.team4096.engine.motion.util

import edu.wpi.first.wpilibj.PIDSourceType
import frc.team4096.robot.misc.MiscConsts
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

class PIDVAController(
	val pidvaVals: PIDVAVals,
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
					pidvaVals.kP * error +
					pidvaVals.kI * integral +
					pidvaVals.kD * derivative
				)

				lastError = error

				// Wait 20ms (50Hz)
				delay((MiscConsts.K_DT * 1000).toInt())
			}
		}
	}

	fun disable() { isEnabled = false }
}