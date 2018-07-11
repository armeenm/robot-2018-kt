package frc.team4096.robot.drivetrain

import kotlin.math.PI

object DriveConsts {
	// Motors
	const val PWM_L1 = 2
	const val PWM_L2 = 3
	const val PWM_R1 = 0
	const val PWM_R2 = 1

	// Sensors
	const val L_ENC_CHANNEL_A = 0
	const val L_ENC_CHANNEL_B = 1
	const val R_ENC_CHANNEL_A = 2
	const val R_ENC_CHANNEL_B = 3

	// Pneumatics
	const val PCM_SHIFTER_1 = 0
	const val PCM_SHIFTER_2 = 7

	// Hardware
	const val MAX_VEL = 9.0
	const val MAX_ACCEL = 4.0
	const val MAX_JERK = 150.0

	const val WHEELBASE_WIDTH = 2.3
	const val WHEEL_DIAMETER = 6.25 / 12 // Feet

	const val ENC_TICKS_PER_REV = 256
	const val ENC_TICKS_PER_FOOT =
		ENC_TICKS_PER_REV / (PI * WHEEL_DIAMETER)

	// Software
	const val CORRECTION_THRESHOLD = 0.05
	const val CORRECTION_FORWARD_kP = 0.1
	const val CORRECTION_REVERSE_kP = 0.04

	const val ROTATION_CAP = 0.85

	const val ROTATION_MIN_POWER = 0.5
	const val DRIVE_MIN_POWER = 0.5

	const val ROTATION_kP = 0.01
	const val ROTATION_kI = 0
	const val ROTATION_kD = 0

	const val DRIVE_DISTANCE_kP = 0.35
	const val DRIVE_DISTANCE_kI = 0
	const val DRIVE_DISTANCE_kD = 0
}