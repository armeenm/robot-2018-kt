package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.team4096.engine.async.AsyncLooper
import frc.team4096.engine.extensions.wpi.ZedSubsystem
import frc.team4096.engine.kinematics.Pose2D
import frc.team4096.engine.math.avg
import frc.team4096.engine.math.boundRadians
import frc.team4096.engine.math.toDegrees
import frc.team4096.engine.math.toRadians
import frc.team4096.engine.motion.ControlState
import frc.team4096.engine.motion.DriveMode
import frc.team4096.engine.motion.DriveSignal
import frc.team4096.engine.motion.EncDistances
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
    private val leftMotor1 = VictorSP(DriveConsts.PWM_VICTOR_L1).apply { inverted = DriveConsts.INVERT_L1 }
    private val leftMotor2 = VictorSP(DriveConsts.PWM_VICTOR_L2).apply { inverted = DriveConsts.INVERT_L2 }
    val leftMotorGroup = SpeedControllerGroup(leftMotor1, leftMotor2)

    private val rightMotor1 = VictorSP(DriveConsts.PWM_VICTOR_R1).apply { inverted = DriveConsts.INVERT_R1 }
    private val rightMotor2 = VictorSP(DriveConsts.PWM_VICTOR_R2).apply { inverted = DriveConsts.INVERT_R2 }
    val rightMotorGroup = SpeedControllerGroup(rightMotor1, rightMotor2)

    val diffDrive = DifferentialDrive(leftMotorGroup, rightMotorGroup)

    private val shifterSolenoid = DoubleSolenoid(
            MiscConsts.CAN_PCM,
            DriveConsts.PCM_SHIFTER_1,
            DriveConsts.PCM_SHIFTER_2
    )

    val leftEncoder = Encoder(DriveConsts.LEFT_ENCODER_A, DriveConsts.LEFT_ENCODER_B).apply {
        setReverseDirection(DriveConsts.INVERT_LEFT_ENC)
    }
    val rightEncoder = Encoder(DriveConsts.RIGHT_ENCODER_A, DriveConsts.RIGHT_ENCODER_B).apply {
        setReverseDirection(DriveConsts.INVERT_RIGHT_ENC)
    }

    var signal = DriveSignal(0.0, 0.0, false)
        set(sig) {
            sig.xSpeed = applyDeadband(sig.xSpeed, DriveConsts.SPEED_DEADBAND)
            sig.zRotation = applyDeadband(sig.zRotation, DriveConsts.ROT_DEADBAND)
            when (driveMode) {
                DriveMode.ARCADE -> diffDrive.arcadeDrive(sig.xSpeed, sig.zRotation)
                DriveMode.CURVATURE -> diffDrive.curvatureDrive(sig.xSpeed, sig.zRotation, sig.isQuickTurn)
            }
            field = sig
        }

    // Assumes starting at the origin facing forward.
    // TODO: Change this based on robot starting position in auto.
    var pose = Pose2D(0.0, 0.0, 0.0)
    val poseLoop = AsyncLooper(100.0, false) { updatePose() }

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
        leftEncoder.distancePerPulse = 1 / DriveConsts.ENCODER_TPF
        rightEncoder.distancePerPulse = 1 / DriveConsts.ENCODER_TPF

        reset()
        poseLoop.start()
    }

    override fun reset() {
        gear = GearState.HIGH

        leftEncoder.reset()
        rightEncoder.reset()
        encDistances.leftDistance = 0.0
        encDistances.rightDistance = 0.0
        pose = Pose2D()
        Gyro.reset()
    }

    override fun log() {
        /*
        println("X: ${pose.translation.x}, Y: ${pose.translation.y}, Theta: ${pose.rotation.degrees}" +
                ", Left: ${leftEncoder.distance}, Right: ${rightEncoder.distance}, Gyro: ${Gyro.angle} ")
                */
    }

    // Methods
    override fun stop() {
        signal = DriveSignal(0.0, 0.0, signal.isQuickTurn)
    }

    private fun updatePose() {
        // Get the delta by making a new EncDistances object with the latest distances
        val newDistances = EncDistances(leftEncoder.distance, rightEncoder.distance)
        val deltaEncDistances = newDistances - encDistances
        val avgEncDistance = deltaEncDistances.average()
        encDistances = newDistances

        // Update pose using basic trigonometry
        val angle = Gyro.angle.toRadians().boundRadians()
        pose = Pose2D(
                pose.translation.x + avgEncDistance * cos(angle),
                pose.translation.y + avgEncDistance * sin(angle),
                angle
        )
    }
}
