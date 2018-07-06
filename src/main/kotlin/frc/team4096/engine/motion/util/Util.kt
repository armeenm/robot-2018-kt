package frc.team4096.engine.motion.util

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