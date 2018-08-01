package frc.team4096.engine.motion

import com.sun.org.apache.xpath.internal.operations.Bool

/**
 * Structure to store PVAJ data.
 *
 * @param pos Position
 * @param vel Velocity
 * @param accel Acceleration
 * @param jerk Jerk
 */
data class PVAJData(
	var pos: Double = 0.0,
	var vel: Double = 0.0,
	var accel: Double = 0.0,
	var jerk: Double = 0.0
)

/**
 * Structure to store PID gain and VA feedforward data.
 *
 * @param kP Proportion Gain
 * @param kI Integral Gain
 * @param kD Derivative Gain
 * @param kV Velocity Gain
 * @param kA Acceleration Gain
 */
data class PIDVAVals(
	var kP: Double = 0.0,
	var kI: Double = 0.0,
	var kD: Double = 0.0,
	var kF: Double = 0.0,
	var kV: Double = 0.0,
	var kA: Double = 0.0
)

/**
 * General control state enum to be used in state machines.
 */
enum class ControlState {
	OPEN_LOOP,
	CURRENT_CONTROL,
	VELOCITY_CONTROL,
	POSITION_CONTROL
}

data class DriveSignal(var xSpeed: Double, var zRotation: Double, var isQuickTurn: Boolean)

data class DrivePose(var xPos: Double, var yPos: Double, var yawAngle: Double)
data class EncDistances(var leftDistance: Double, var rightDistance: Double) {
	operator fun minus(incEncDistances: EncDistances) =
		EncDistances(
			incEncDistances.leftDistance - leftDistance,
			incEncDistances.rightDistance - rightDistance
		)

	operator fun plus(incEncDistances: EncDistances) =
		EncDistances(
			incEncDistances.leftDistance + leftDistance,
			incEncDistances.rightDistance + rightDistance
		)

	fun average() = (leftDistance + rightDistance) / 2
}

// Enums
enum class DriveMode {
	ARCADE,
	CURVATURE
}
