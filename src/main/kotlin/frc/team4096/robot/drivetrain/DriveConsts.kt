package frc.team4096.robot.drivetrain

import frc.team4096.engine.motion.PIDVAVals
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
    const val ROTATION_DEADBAND = 0.05
    const val SPEED_DEADBAND = 0.05

    const val ROTATION_SQUARED = false
    const val SPEED_SQUARED = true

    /**
     * PIDVA values for Pathfinder v1.
     */
    val PIDVA_PF = PIDVAVals(
            kP = 0.3,
            kI = 0.0,
            kD = 0.0,
            kV = 1 / MAX_VEL,
            kA = 0.0
    )

    // Ramsete NLTV Ref. Tracker Constants
    const val RAMSETE_B = 0.5
    const val RAMSETE_ZETA = 0.175
}
