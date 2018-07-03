package frc.team4096.engine.motion

import edu.wpi.first.wpilibj.Timer
import frc.team4096.engine.motion.util.PIDFVals
import frc.team4096.robot.misc.MiscConsts.K_DT

/**
 * A trapezoidal motion profile.
 * Consists of vel ramp up, cruise, and vel ramp down periods.
 * For more information, refer to 254 champs video.
 *
 * @param targetPos Target position
 * @param maxVel Maximum velocity
 * @param maxAccel Maximum acceleration
 * @constructor Constructor
 */
class TrapezoidalMP(
	val targetPos: Double,
	val maxVel: Double,
	val maxAccel: Double
) {

	private enum class ProfileState { REST, ACCEL, CRUISE, DECEL }

	val tAccel = maxVel / maxAccel
	val xAccel = 0.5 * maxVel * tAccel

	val xCruise = targetPos - 2 * xAccel
	val tCruise = xCruise / maxVel

	private var state = ProfileState.REST

	var error = 0.0
	var integral = 0.0
	var goalVel = 0.0
	var goalPos = 0.0

	var startTime = 0.0
	var curTime = 0.0

	var isFinished = false

	fun initFollower() {
		startTime = Timer.getFPGATimestamp()
		state = ProfileState.ACCEL

		error = 0.0
		integral = 0.0
		goalVel = 0.0
		goalPos = 0.0
	}

	private fun updateState() {
		val diff = curTime - startTime
	}

	fun follow(pidfVals: PIDFVals, sourceFun: () -> Double, sinkFun: (Double) -> Unit) {
		curTime = Timer.getFPGATimestamp()

		updateState()

		when (state) {
			ProfileState.ACCEL -> {
				goalVel += maxAccel * K_DT
				goalPos += goalVel * K_DT
			}

			ProfileState.CRUISE ->
				goalPos += goalVel * K_DT

			ProfileState.DECEL -> {
				goalVel -= maxAccel * K_DT
				goalPos -= goalVel * K_DT
			}

			ProfileState.REST ->
				isFinished = true
		}

		error = goalPos - sourceFun()
		integral += (error * K_DT)

		sinkFun(
			pidfVals.kP * error +
				pidfVals.kI * integral +
				pidfVals.kF * goalVel
		)
	}
}
