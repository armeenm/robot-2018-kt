package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.team4096.engine.async.AsyncLooper
import frc.team4096.engine.extensions.wpi.ZedSubsystem
import frc.team4096.engine.kinematics.Pose2D
import frc.team4096.engine.math.boundRadians
import frc.team4096.engine.math.toRadians
import frc.team4096.engine.motion.*
import frc.team4096.engine.util.applyDeadband
import frc.team4096.robot.misc.MiscConsts
import frc.team4096.robot.sensors.Gyro
import kotlin.math.cos
import kotlin.math.sin

/**
 * Drivetrain subsystem.
 * Handles driving and shifting.
 */
object DriveSubsystem : ZedSubsystem() {
    // Hardware
    private val leftMotor1 = VictorSP(DriveConsts.kPWMVictorL1)
    private val leftMotor2 = VictorSP(DriveConsts.kPWMVictorL2)
    val leftMotorGroup = SpeedControllerGroup(leftMotor1, leftMotor2)

    private val rightMotor1 = VictorSP(DriveConsts.kPWMVictorR1)
    private val rightMotor2 = VictorSP(DriveConsts.kPWMVictorR2)
    val rightMotorGroup = SpeedControllerGroup(rightMotor1, rightMotor2)

    val diffDrive = DifferentialDrive(leftMotorGroup, rightMotorGroup)

    private val shifterSolenoid = DoubleSolenoid(
            MiscConsts.CAN_PCM,
            DriveConsts.kPCMShifter1,
            DriveConsts.kPCMShifter2
    )

    val leftEncoder = Encoder(DriveConsts.kLeftEncoderA, DriveConsts.kLeftEncoderB)
    val rightEncoder = Encoder(DriveConsts.kRightEncoderA, DriveConsts.kRightEncoderB)

    var signal = DriveSignal(0.0, 0.0, isQuickTurn = false)
        set(sig) {
            sig.xSpeed = applyDeadband(sig.xSpeed, DriveConsts.kSpeedDeadband)
            sig.zRotation = applyDeadband(sig.xSpeed, DriveConsts.kRotDeadband)
            when (driveMode) {
                DriveMode.ARCADE -> diffDrive.arcadeDrive(sig.xSpeed, sig.zRotation)
                DriveMode.CURVATURE -> diffDrive.curvatureDrive(sig.xSpeed, sig.zRotation, sig.isQuickTurn)
            }
            field = sig
        }

    // Assumes starting at the origin facing forward.
    // TODO: Change this based on robot starting position in auto.
    var pose = Pose2D(0.0, 0.0, 0.0)

    var encDistances = EncDistances(leftEncoder.distance, rightEncoder.distance)

    // Hardware states
    var gear = GearState.NEUTRAL
        set(inputState) {
            shifterSolenoid.set(inputState.solenoidState)
            field = inputState
        }

    // Software States
    var controlState = ControlState.OPEN_LOOP
    var driveMode = DriveMode.CURVATURE

    // Required Methods
    init {
        leftEncoder.distancePerPulse = 1 / DriveConsts.kEncTicksPerFoot
        rightEncoder.distancePerPulse = 1 / DriveConsts.kEncTicksPerFoot

        reset()

        val poseLoop = AsyncLooper(250.0, false) { updatePose() }
    }

    override fun reset() {
        gear = GearState.HIGH

        leftEncoder.reset()
        rightEncoder.reset()
        encDistances.leftDistance = 0.0
        encDistances.rightDistance = 0.0

    }

    override fun log() {
        TODO("not implemented")
    }

    // Methods
    override fun stop() {
        signal = DriveSignal(0.0, 0.0, signal.isQuickTurn)
    }

    private fun updatePose() {
        // Get the delta by making a new EncDistances object with the latest distances
        // Makes use of operator overloading in the data class
        val deltaEncDistances = EncDistances(leftEncoder.distance, rightEncoder.distance) - encDistances
        val avgEncDistance = deltaEncDistances.average()

        // Update pose using basic trigonometry
        pose = Pose2D(
                avgEncDistance * cos(Gyro.angle.toRadians()),
                avgEncDistance * sin(Gyro.angle.toRadians()),
                Gyro.angle.toRadians().boundRadians()
        )
    }
}
