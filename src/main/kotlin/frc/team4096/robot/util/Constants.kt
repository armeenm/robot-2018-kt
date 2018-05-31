@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package frc.team4096.robot.util

import kotlin.math.PI

object DriveConsts {
	// Motors
	const val PWM_L1 = 2
	const val PWM_L2 = 3
	const val PWM_R1 = 0
	const val PWM_R2 = 1

	// Sensors

	// Pneumatics
	const val PCM_SHIFTER_1 = 0
	const val PCM_SHIFTER_2 = 7

	// Hardware
	const val MAX_VEL = 9
	const val MAX_ACCEL = 9
	const val MAX_JERK = 150

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

	// Software
	val POSITION_LIST = listOf(0, 0.25, 2, 6.5)
}

object ClimberConsts {
	// Motors
	const val PWM_MOTOR = 5
	const val PWM_SERVO = 9

	// Speeds
	const val MAX_FORWARD_SPEED = 1.0
	const val MAX_REVERSE_SPEED = -1.0

	// Software
	const val SERVO_RELEASE_ANGLE = 90.0
}

object MiscConsts {
	// CAN
	const val CAN_PCM = 2
	const val CAN_PDP = 0

	// Analog
	const val AIN_PRESSURE = 0
}

object XboxConsts {
	// Joystick Deadband
	const val DEAD_BAND = 0.15

	// Buttons
	enum class Button(val id: Int) {
		A(1),
		B(2),
		X(3),
		Y(4),
		LB(5),
		RB(6),
		SELECT(7),
		START(8),
		LEFT_STICK(9),
		RIGHT_STICK(10)
	}

	// Axes
	enum class Axis(val id: Int) {
		LEFT_X(0),
		LEFT_Y(1),
		RIGHT_X(4),
		RIGHT_Y(5),
		LT(2),
		RT(3)
	}

	// D-Pad
	enum class DPad(val id: Int) {
		NONE(-1),
		UP(0),
		UP_RIGHT(45),
		RIGHT(90),
		DOWN_RIGHT(135),
		DOWN(180),
		DOWN_LEFT(225),
		LEFT(270),
		UP_LEFT(315)
	}

	// Axis Inversion
	const val INVERT_LEFT_X = true
	const val INVERT_LEFT_Y = false
	const val INVERT_RIGHT_X = true
	const val INVERT_RIGHT_Y = true
}

const val RAP_LYRICS = """
Pressure's weak
Feeder's heavy
Scratches on his paint already
Overload me! (code's spaghetti)

But on the outside he looks fierce, Jaw-Z
To shoot balls
But he keeps on missing
When he broke down, the whole crowd groans so loud
He closes his valve, but the air just leaks out
He's broken now, Ctrl-Z is mopin' now
The power's run out, low bar's pressin down

Snap back to the tragedy, oh there goes the battery
Oh there goes belt, it broke
He's so bad but he won't be fixed easy, no
"""
