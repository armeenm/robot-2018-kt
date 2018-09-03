package frc.team4096.robot.drivetrain

import frc.team4096.engine.motion.PIDVAVals
import kotlin.math.PI

object DriveConsts {
    // Motors
    const val PWM_VICTOR_L1 = 2
    const val PWM_VICTOR_L2 = 3
    const val PWM_VICTOR_R1 = 0
    const val PWM_VICTOR_R2 = 1

    // Sensors
    const val LEFT_ENCODER_A = 0
    const val LEFT_ENCODER_B = 1
    const val RIGHT_ENCODER_A = 2
    const val RIGHT_ENCODER_B = 3

    // Pneumatics
    const val PCM_SHIFTER_1 = 0
    const val PCM_SHIFTER_2 = 7

    // Hardware
    const val DT_MAX_VEL = 9.0
    const val DT_MAX_ACCEL = 4.0
    const val DT_MAX_JERK = 150.0

    const val DT_TRACK_WIDTH = 2.3
    const val DT_WHEEL_DIAMETER = 6.25 / 12 // Feet

    /**
     * Encoder ticks per revolution
     */
    const val ENCODER_TPR = 256
    /**
     * Encoder ticks per foot
     */
    const val ENCODER_TPF =
            ENCODER_TPR / (PI * DT_WHEEL_DIAMETER)

    // Software
    const val ROT_DEADBAND = 0.05
    const val SPEED_DEADBAND = 0.05

    const val IS_ROT_SQUARED = false
    const val IS_SPEED_SQUARED = true

    /**
     * PIDVA values for Pathfinder v1.
     */
    val PIDVA_GAINS_PF = PIDVAVals(
            kP = 0.3,
            kI = 0.0,
            kD = 0.0,
            kV = 1 / DT_MAX_VEL,
            kA = 0.0
    )

    // Ramsete NLTV Ref. Tracker Constants
    const val BETA = 0.5
    const val ZETA = 0.175
}
