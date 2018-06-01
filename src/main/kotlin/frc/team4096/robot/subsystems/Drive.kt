package frc.team4096.robot.subsystems

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.drive.DifferentialDrive

import frc.team4096.robot.OI
import frc.team4096.robot.commands.CurvatureDrive
import frc.team4096.robot.util.ControlState
import frc.team4096.robot.util.DriveConsts
import frc.team4096.robot.util.MiscConsts
import frc.team4096.robot.util.XboxConsts

object DriveSubsystem: Subsystem() {
	// Hardware
	var leftMotor1 = VictorSP(DriveConsts.PWM_L1)
	var leftMotor2 = VictorSP(DriveConsts.PWM_L2)
	var leftMotorGroup = SpeedControllerGroup(leftMotor1, leftMotor2)

	var rightMotor1 = VictorSP(DriveConsts.PWM_R1)
	var rightMotor2 = VictorSP(DriveConsts.PWM_R2)
	var rightMotorGroup = SpeedControllerGroup(rightMotor1, rightMotor2)

	var diffDrive = DifferentialDrive(leftMotorGroup, rightMotorGroup)

	var shifterSolenoid = DoubleSolenoid(
			MiscConsts.CAN_PCM,
			DriveConsts.PCM_SHIFTER_1,
			DriveConsts.PCM_SHIFTER_2
	)

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
	var xSpeed: Double = 0.0
	var zRotation: Double = 0.0

	init { reset() }

	// Set stuff up
	private fun reset() {
		gear = GearState.HIGH
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
		this.xSpeed = xSpeed
		this.zRotation = zRotation
		this.isQuickTurn = isQuickTurn

		this.diffDrive.curvatureDrive(xSpeed, zRotation, isQuickTurn)
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
