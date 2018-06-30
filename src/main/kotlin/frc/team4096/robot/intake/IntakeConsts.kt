package frc.team4096.robot.intake

object IntakeConsts {
	// Joystick Deadband
	const val WHEEL_DEAD_BAND = 0.05
	const val ROTATE_DEAD_BAND = 0.05

	// Motors
	const val PWM_WHEELS_MOTOR = 4
	const val PWM_ROTATE_MOTOR = 6

	const val PDP_WHEELS = 7

	// Pneumatics
	const val PCM_SQUEEZE_1 = 2
	const val PCM_SQUEEZE_2 = 5

	// Speeds
	const val MAX_IN_SPEED = 1.0
	const val MAX_OUT_SPEED = -1.0
	const val DEFAULT_ROTATE_SPEED = 0.75

	const val ROTATE_HOLD_SPEED = 0.3
}