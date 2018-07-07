package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.team4096.engine.motion.util.ControlState
import frc.team4096.engine.wpi.ZedSubsystem
import frc.team4096.robot.Robot
import frc.team4096.robot.misc.MiscConsts
import frc.team4096.robot.misc.XboxConsts
import frc.team4096.robot.oi.OIMain
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

	/**
	 * 254-style drive signal.
	 * Lacks brake due to motor controller capabilities.
	 */
	private var signal = DriveSignal(0.0, 0.0)

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
	var isQuickTurn = false

	// Software States
	var controlState = ControlState.OPEN_LOOP
	var driveMode = DriveMode.CURVATURE
	var wasCorrecting = false

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

	override fun initDefaultCommand() {
		CurvatureDriveCmd(
			OIMain.XboxController1.getAxis(XboxConsts.Axis.LEFT_Y),
			OIMain.XboxController1.getAxis(XboxConsts.Axis.RIGHT_X),
			OIMain.XboxController1.lbButton.get()
		)
	}

	// Methods
	fun stop() = diffDrive.tankDrive(0.0, 0.0)

	fun curvatureDrive(xSpeed: Double, zRotation: Double, isQuickTurn: Boolean) {
		// Update signal
		signal.xSpeed = xSpeed
		signal.zRotation = zRotation

		// Update quick turn state
		DriveSubsystem.isQuickTurn = isQuickTurn

		diffDrive.curvatureDrive(xSpeed, zRotation, isQuickTurn)
	}

	private fun updatePose() {
		// Get the delta by making a new EncDistances object with the latest distances
		// Makes use of operator overloading in the data class
		val deltaEncDistances = EncDistances(leftEncoder.distance, rightEncoder.distance) - encDistances
		val avgEncDistance = deltaEncDistances.average()

		// Update pose using basic trigonometry
		pose.xPos = avgEncDistance * cos(Robot.gyro.angle)
		pose.yPos = avgEncDistance * sin(Robot.gyro.angle)
		pose.yawAngle = Robot.gyro.angle
	}

	// Data Classes
	data class DriveSignal(var xSpeed: Double, var zRotation: Double)

	data class DrivePose(var xPos: Double, var yPos: Double, var yawAngle: Double)
	data class EncDistances(var leftDistance: Double, var rightDistance: Double) {
		operator fun minus(incEncDistances: EncDistances) =
			EncDistances(
				incEncDistances.leftDistance - leftDistance,
				incEncDistances.rightDistance - rightDistance
			)

		operator fun plus(incEncDistances: EncDistances) =
			EncDistances(
				incEncDistances.leftDistance + leftDistance,
				incEncDistances.rightDistance + rightDistance
			)

		fun average() = (leftDistance + rightDistance) / 2
	}

	// Enums
	enum class DriveMode {
		TANK,
		ARCADE,
		CURVATURE
	}

	enum class GearState(val solenoidState: DoubleSolenoid.Value) {
		HIGH(DoubleSolenoid.Value.kForward),
		LOW(DoubleSolenoid.Value.kReverse),
		NEUTRAL(DoubleSolenoid.Value.kOff)
	}
}
