package frc.team4096.engine.motion.util

data class PVAJData(
	var pos: Double = 0.0,
	var vel: Double = 0.0,
	var accel: Double = 0.0,
	var jerk: Double = 0.0
)

data class PIDFVals(
	var kP: Double = 0.0,
	var kI: Double = 0.0,
	var kD: Double = 0.0,
	var kF: Double = 0.0
)

data class PIDVAVals(
	var kP: Double = 0.0,
	var kI: Double = 0.0,
	var kD: Double = 0.0,
	var kV: Double = 0.0,
	var kA: Double = 0.0
)

enum class ControlState {
	OPEN_LOOP,
	CURRENT_CONTROL,
	VELOCITY_CONTROL,
	POSITION_CONTROL
}