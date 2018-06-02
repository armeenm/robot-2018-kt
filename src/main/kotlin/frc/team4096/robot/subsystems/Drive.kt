package frc.team4096.robot.subsystems

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.Encoder
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.drive.DifferentialDrive

import frc.team4096.robot.OI
import frc.team4096.robot.Robot
import frc.team4096.robot.commands.CurvatureDrive
import frc.team4096.robot.util.ControlState
import frc.team4096.robot.util.DriveConsts
import frc.team4096.robot.util.MiscConsts
import frc.team4096.robot.util.XboxConsts
import kotlin.math.cos
import kotlin.math.sin

object DriveSubsystem: Subsystem() {
	// Hardware
	val leftMotor1 = VictorSP(DriveConsts.PWM_L1)
	val leftMotor2 = VictorSP(DriveConsts.PWM_L2)
	val leftMotorGroup = SpeedControllerGroup(leftMotor1, leftMotor2)

	val rightMotor1 = VictorSP(DriveConsts.PWM_R1)
	val rightMotor2 = VictorSP(DriveConsts.PWM_R2)
	val rightMotorGroup = SpeedControllerGroup(rightMotor1, rightMotor2)

	val diffDrive = DifferentialDrive(leftMotorGroup, rightMotorGroup)

	val shifterSolenoid = DoubleSolenoid(
			MiscConsts.CAN_PCM,
			DriveConsts.PCM_SHIFTER_1,
			DriveConsts.PCM_SHIFTER_2
	)

	val leftEncoder = Encoder(DriveConsts.L_ENC_CHANNEL_A, DriveConsts.L_ENC_CHANNEL_B)
	val rightEncoder = Encoder(DriveConsts.R_ENC_CHANNEL_A, DriveConsts.R_ENC_CHANNEL_B)

	// 254-style drive signal (without brake because Victor SPs :/)
	val signal = DriveSignal(0.0, 0.0)

	// Assumes starting at the origin facing forward.
	// TODO: Change this based on robot starting position in auto.
	val pose = DrivePose(0.0, 0.0, 0.0)

	var encDistances = EncDistances(leftEncoder.distance, rightEncoder.distance)

	// Hardware states
	var gear = GearState.NEUTRAL
		set(inputState) {
			shifterSolenoid.set(inputState.solenoidState)
			field = inputState
		}
	var isQuickTurn = false

	// Software states
	var controlState = ControlState.OPEN_LOOP
	var driveMode = DriveMode.CURVATURE
	var wasCorrecting = false

	// Motor values

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

	fun autoReset() {
		reset()
	}

	fun teleopReset() {
		reset()
	}

	override fun initDefaultCommand() {
		CurvatureDrive(
				OI.XboxController1.getAxis(XboxConsts.Axis.LEFT_Y),
				OI.XboxController1.getAxis(XboxConsts.Axis.RIGHT_X),
				OI.XboxController1.lbButton.get()
		)
	}

	fun toggleGearState() {
		gear = when (gear) {
			GearState.HIGH -> GearState.LOW
			GearState.LOW -> GearState.HIGH
			GearState.NEUTRAL -> {
				println("Shifting from neutral!")
				GearState.HIGH
			}
		}
	}

	fun stop() = this.diffDrive.tankDrive(0.0, 0.0)

	fun curvatureDrive(xSpeed: Double, zRotation: Double, isQuickTurn: Boolean) {
		// Update signal
		this.signal.xSpeed = xSpeed; this.signal.zRotation = zRotation
		// Update quick turn state
		this.isQuickTurn = isQuickTurn

		this.diffDrive.curvatureDrive(xSpeed, zRotation, isQuickTurn)
	}

	fun updatePose() {
		// Get the delta by making a new EncDistances object with the latest distances
		// Makes use of operator overloading in the data class
		var deltaEncDistances = EncDistances(leftEncoder.distance, rightEncoder.distance) - encDistances
		val avgEncDistance = deltaEncDistances.average()

		pose.xPos = avgEncDistance * cos(Robot.gyro.angle)
		pose.yPos = avgEncDistance * sin(Robot.gyro.angle)
		pose.yawAngle = Robot.gyro.angle
	}
}

enum class DriveMode {
	TANK,
	ARCADE,
	CURVATURE
}

enum class GearState(val solenoidState: DoubleSolenoid.Value) {
	HIGH(DoubleSolenoid.Value.kForward),
	LOW(DoubleSolenoid.Value.kReverse),
	NEUTRAL(DoubleSolenoid.Value.kOff);
}

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
