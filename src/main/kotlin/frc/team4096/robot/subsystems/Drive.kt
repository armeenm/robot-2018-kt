package frc.team4096.robot.subsystems

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.command.Subsystem
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.drive.DifferentialDrive
import frc.team4096.robot.OI
import frc.team4096.robot.commands.CurvatureDrive

import frc.team4096.robot.util.DriveConsts
import frc.team4096.robot.util.MiscConsts
import frc.team4096.robot.util.XboxConsts

object DriveSubsystem: Subsystem() {
	// Hardware
	var left1 = VictorSP(DriveConsts.PWM_L1)
	var left2 = VictorSP(DriveConsts.PWM_L2)
	var leftGroup = SpeedControllerGroup(left1, left2)

	var right1 = VictorSP(DriveConsts.PWM_R1)
	var right2 = VictorSP(DriveConsts.PWM_R2)
	var rightGroup = SpeedControllerGroup(right1, right2)

	var diffDrive = DifferentialDrive(leftGroup, rightGroup)

	var shifterSolenoid = DoubleSolenoid(
			MiscConsts.CAN_PCM,
			DriveConsts.PCM_SHIFTER_1,
			DriveConsts.PCM_SHIFTER_2
	)

	// Hardware states
	var gear = GearState.NEUTRAL
	var isQuickTurn = false

	// Software states
	var state = DriveState.OPEN_LOOP
	var mode = DriveMode.CURVATURE
	var wasCorrecting = false

	// Values
	var xSpeed: Double = 0.0
	var zRotation: Double = 0.0

	init {
		// Set high gear
		setGearState(GearState.HIGH)
	}

	override fun initDefaultCommand() {
		CurvatureDrive(
				OI.getAxis(OI.XboxController1, XboxConsts.Axis.LEFT_Y),
				OI.getAxis(OI.XboxController1, XboxConsts.Axis.RIGHT_X),
				OI.XboxController1.getBumper(GenericHID.Hand.kLeft)
		)
	}

	// Used to fix internal gear state in case someone manually shifted
	private fun updateGearState() {
		gear = GearState.fromInt(shifterSolenoid.get())
	}

	fun setGearState(state: GearState) {
		shifterSolenoid.set(state.solenoidState)
		gear = state
	}

	fun toggleGearState() {
		when (gear) {
			GearState.HIGH -> setGearState(GearState.LOW)
			GearState.LOW -> setGearState(GearState.HIGH)
			else -> throw kotlin.Exception("Unable to toggle gear state")
		}
	}

	fun stop() = this.diffDrive.tankDrive(0, 0)

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

enum class DriveState {
	OPEN_LOOP,
	POSITION_CONTROL,
	PATH_FOLLOWING
}

enum class GearState(val solenoidState: DoubleSolenoid.Value) {
	HIGH(DoubleSolenoid.Value.kForward),
	LOW(DoubleSolenoid.Value.kReverse),
	NEUTRAL(DoubleSolenoid.Value.kOff);

	// Reverse lookup companion object
	companion object {
		// Create mapping from value (DoubleSolenoid.Value) to GearState
		private val map =
				GearState.values().associateBy(GearState::solenoidState)

		// Function to return value from map
		fun fromInt(type: DoubleSolenoid.Value) =
			map[type] ?: throw IllegalArgumentException()
	}
}
