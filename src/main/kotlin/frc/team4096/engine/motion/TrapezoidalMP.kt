package frc.team4096.engine.motion

import frc.team4096.engine.motion.util.PVAJData
import frc.team4096.robot.misc.MiscConsts.K_DT
import kotlin.math.sqrt

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
	private val targetPos: Double,
	private val maxVel: Double,
	private val maxAccel: Double,
	override val source: () -> Double,
	override val sink: (Double) -> Unit,
	override val freq: Double = 50.0
) : MotionProfile() {

	private enum class ProfileState { REST, ACCEL, CRUISE, DECEL }

	var tAccel = maxVel / maxAccel
	var xAccel = 0.5 * maxVel * tAccel

	var xCruise = 0.0
	var tCruise = 0.0

	private val dt = 1 / freq

	init {
		if (2 * xAccel < targetPos) {
			// Trapezoidal
			println("zoid")
			xCruise = targetPos - 2 * xAccel
			tCruise = xCruise / maxVel
		} else {
			// Triangular
			println("triang")
			tAccel = sqrt(targetPos / maxAccel)
			xAccel = targetPos / 2
		}
	}

	private var state = ProfileState.REST

	private var error = 0.0
	private var integral = 0.0
	private val pvajData = PVAJData()

	override var isFinished = false

	fun reset() {
		state = ProfileState.REST

		error = 0.0
		integral = 0.0
		pvajData.apply {
			pos = 0.0
			vel = 0.0
			accel = 0.0
		}
	}

	private fun updateState(curPos: Double) {
		state = when {
			curPos < xAccel -> ProfileState.ACCEL
			curPos < xAccel + xCruise -> ProfileState.CRUISE
			curPos < 2 * xAccel + xCruise -> ProfileState.DECEL
			else -> ProfileState.REST
		}
	}

	override fun follow(curPos: Double): PVAJData {
		updateState(curPos)
		when (state) {
			ProfileState.ACCEL -> {
				pvajData.vel += maxAccel * dt
				pvajData.pos += pvajData.vel * dt
			}

			ProfileState.CRUISE ->
				pvajData.pos += pvajData.vel * dt

			ProfileState.DECEL -> {
				pvajData.vel -= maxAccel * dt
				pvajData.pos -= pvajData.vel * dt
			}

			ProfileState.REST -> {
				if (!isFinished) reset()
				isFinished = true
			}
		}
		return pvajData
	}
}
