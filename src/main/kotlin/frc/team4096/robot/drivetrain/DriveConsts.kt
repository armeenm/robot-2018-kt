package frc.team4096.robot.drivetrain

import frc.team4096.engine.motion.PIDVAVals
import kotlin.math.PI

object DriveConsts {
    // Motors
    const val kPWMVictorL1 = 2
    const val kPWMVictorL2 = 3
    const val kPWMVictorR1 = 0
    const val kPWMVictorR2 = 1

    // Sensors
    const val kLeftEncoderA = 0
    const val kLeftEncoderB = 1
    const val kRightEncoderA = 2
    const val kRightEncoderB = 3

    // Pneumatics
    const val kPCMShifter1 = 0
    const val kPCMShifter2 = 7

    // Hardware
    const val kMaxVel = 9.0
    const val kMaxAccel = 4.0
    const val kMaxJerk = 150.0

    const val kTrackWidth = 2.3
    const val kWheelDiameter = 6.25 / 12 // Feet

    const val kEncoderTicksPerRev = 256
    const val kEncTicksPerFoot =
            kEncoderTicksPerRev / (PI * kWheelDiameter)

    // Software
    const val kRotDeadband = 0.05
    const val kSpeedDeadband = 0.05

    const val kIsRotSquared = false
    const val kIsSpeedSquared = true

    /**
     * PIDVA values for Pathfinder v1.
     */
    val kPIDVAGainsPF = PIDVAVals(
            kP = 0.3,
            kI = 0.0,
            kD = 0.0,
            kV = 1 / kMaxVel,
            kA = 0.0
    )

    // Ramsete NLTV Ref. Tracker Constants
    const val kBeta = 0.5
    const val kZeta = 0.175
}
