package frc.team4096.robot.util

import edu.wpi.first.wpilibj.PIDSourceType
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

// PIDVA trapezoidal motion profile
class TrapezoidalMP(var pidVals: PIDVals, val distance: Double, val cruiseVel: Double, val accel: Double) {
	val accelTime = cruiseVel / accel
	// Distance covered by accel period (integral)
	val accelDist = cruiseVel * accelTime / 2

	val cruiseDist = distance - 2 * accelDist
	val cruiseTime = cruiseDist / cruiseVel
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

class WaitGroup {
    val joinTime: Long by lazy { measureTimeMillis { runBlocking<Unit> { jobs.forEach { it.join() } } } }
    val jobs = mutableListOf<Job>()
    fun add(job: Job): WaitGroup = this.apply { jobs.add(job) }
    fun add(block: suspend () -> Unit): WaitGroup = this.apply { jobs.add(go { block() }) }
    fun join() = this.apply { joinTime }
}

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
				delay(200)
			}
		}
	}
}