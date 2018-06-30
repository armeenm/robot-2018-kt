package frc.team4096.robot.elevator

object ElevatorConsts {
	// Motors
	const val CAN_MASTER_MOTOR = 1
	const val CAN_SLAVE_MOTOR = 3

	// Pneumatics
	const val PCM_BRAKE_1 = 1
	const val PCM_BRAKE_2 = 6

	// Sensors
	const val DIN_LIMIT_SW = 4

	// Hardware
	const val ENC_TICKS_PER_REV = 4096
	const val ENC_TICKS_PER_FOOT = 141000
	const val MAX_OPEN_LOOP_SPEED = 0.6

	// Software
	enum class Positions(val pos: Double) {
		BOTTOM(0.0),
		NO_DRAG(0.25),
		SWITCH(2.0),
		SCALE(6.5)
	}
	const val K_SLOT_ID = 0
	const val DISTANCE_kF = 0.2
	const val DISTANCE_kP = 0.2
	const val DISTANCE_kI = 0.0
	const val DISTANCE_kD = 0.0
	const val MAX_CLOSED_LOOP_ERROR = 5000
	const val MAX_ACCEL = 200000
	const val MAX_CRUISE_VEL = 200000
}