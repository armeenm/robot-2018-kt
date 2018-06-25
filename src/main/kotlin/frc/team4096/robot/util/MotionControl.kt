package frc.team4096.robot.util

import edu.wpi.first.wpilibj.PIDSourceType
import edu.wpi.first.wpilibj.Timer
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

// PIDVA trapezoidal motion profile
class TrapezoidalMP(
	var pidVals: PIDVals,
	val targetPos: Double,
	val cruiseVel: Double,
	val accel: Double
) {

	private enum class MotionState { REST, ACCEL, COAST, DECEL }

	val curTime = { Timer.getFPGATimestamp() }

	var curPos = 0.0
	var curVel = 0.0

	private var curState = MotionState.REST

	private var lastTime = 0.0
	private var startTime = 0.0

	private var lastPos = 0.0
	private var lastVel = 0.0

	/*
	val accelTime = cruiseVel / accel
	val accelDist = cruiseVel * accelTime / 2

	val cruiseDist = distance - 2 * accelDist
	val cruiseTime = cruiseDist / cruiseVel

	val pidController = PIDController(
		pidVals,

	)
	*/
}

// Custom PID controller class
class PIDController(
		var pidVals: PIDVals,
		val setpoint: Double,
		val pidSourceFunc: () -> Double,
		val pidSinkFunc: (Double) -> Unit,
		val pidSourceType: PIDSourceType
) {
	var error = 0.0
	var lastError = 0.0
	var integral = 0.0
	var derivative = 0.0
	var enabled = false

	fun start() {
		launch {
			while (true) {
				if (enabled) {
					error = setpoint - pidSourceFunc()
					integral += (error * 0.2)
					derivative = (error - lastError) / 0.2

					pidSinkFunc(
						pidVals.kP * error +
						pidVals.kI * integral +
						pidVals.kD * derivative +
						pidVals.kF
					)

					lastError = error
				}
				// Wait 20ms (50Hz)
				delay(20)
			}
		}
	}
}