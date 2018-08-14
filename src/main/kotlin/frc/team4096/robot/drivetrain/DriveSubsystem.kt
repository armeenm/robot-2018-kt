package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.team4096.engine.extensions.wpi.ZedSubsystem
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
    private val leftMotor1 = VictorSP(DriveConsts.PWM_L1)
    private val leftMotor2 = VictorSP(DriveConsts.PWM_L2)
    val leftMotorGroup = SpeedControllerGroup(leftMotor1, leftMotor2)

    private val rightMotor1 = VictorSP(DriveConsts.PWM_R1)
    private val rightMotor2 = VictorSP(DriveConsts.PWM_R2)
    val rightMotorGroup = SpeedControllerGroup(rightMotor1, rightMotor2)

    val diffDrive = DifferentialDrive(leftMotorGroup, rightMotorGroup)

    private val shifterSolenoid = DoubleSolenoid(
            MiscConsts.CAN_PCM,
            DriveConsts.PCM_SHIFTER_1,
            DriveConsts.PCM_SHIFTER_2
    )

    val leftEncoder = Encoder(DriveConsts.L_ENC_CHANNEL_A, DriveConsts.L_ENC_CHANNEL_B)
    val rightEncoder = Encoder(DriveConsts.R_ENC_CHANNEL_A, DriveConsts.R_ENC_CHANNEL_B)

    var signal = DriveSignal(0.0, 0.0, isQuickTurn = false)
        set(sig) {
            sig.xSpeed = applyDeadband(sig.xSpeed, DriveConsts.SPEED_DEADBAND)
            sig.zRotation = applyDeadband(sig.xSpeed, DriveConsts.ROTATION_DEADBAND)
            when (driveMode) {
                DriveMode.ARCADE -> diffDrive.arcadeDrive(sig.xSpeed, sig.zRotation)
                DriveMode.CURVATURE -> diffDrive.curvatureDrive(sig.xSpeed, sig.zRotation, sig.isQuickTurn)
            }
            field = sig
        }

    // Assumes starting at the origin facing forward.
    // TODO: Change this based on robot starting position in auto.
    private var pose = DrivePose(0.0, 0.0, 0.0)

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
        leftEncoder.distancePerPulse = 1 / DriveConsts.ENC_TICKS_PER_FOOT
        rightEncoder.distancePerPulse = 1 / DriveConsts.ENC_TICKS_PER_FOOT

        reset()
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

    override fun periodic() {
        updatePose()
    }

    // Set default command in OI
    override fun initDefaultCommand() {}

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
        pose.xPos = avgEncDistance * cos(Gyro.angle)
        pose.yPos = avgEncDistance * sin(Gyro.angle)
        pose.yawAngle = Gyro.angle
    }
}
