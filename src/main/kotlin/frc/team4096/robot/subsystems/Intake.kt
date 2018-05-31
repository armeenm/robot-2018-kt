package frc.team4096.robot.subsystems

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team4096.robot.OI
import frc.team4096.robot.commands.ManualIntake
import frc.team4096.robot.util.*

object IntakeSubsystem: Subsystem() {
	// Hardware
	// TODO: Add second motor controller for wheels
	private var wheelMotor = VictorSP(IntakeConsts.PWM_WHEELS_MOTOR)
	private var rotationMotor = VictorSP(IntakeConsts.PWM_ROTATE_MOTOR)

	private var squeezeSolenoid = DoubleSolenoid(
			MiscConsts.CAN_PCM,
			IntakeConsts.PCM_SQUEEZE_1,
			IntakeConsts.PCM_SQUEEZE_2
	)

	// Hardware states
	var squeeze = SqueezeState.NEUTRAL
		set(inputState) {
			squeezeSolenoid.set(inputState.solenoidState)
			field = inputState
		}

	var cube = HasCube.UNKNOWN
	var isIntakeStalling = false
	var isRotateStalling = false

	var rotateHolding = false
		set(input) {
			rotateSpeed = if (input) IntakeConsts.ROTATE_HOLD_SPEED else 0.0
			field = input
		}

	// Software states
	var wheelControlState = ControlState.OPEN_LOOP
	var rotationControlState = ControlState.OPEN_LOOP

	// Motor values
	var intakeSpeed: Double = 0.0
		set(inputSpeed) {
			applyDeadband(inputSpeed, IntakeConsts.WHEEL_DEAD_BAND)
			wheelMotor.speed = inputSpeed
			field = inputSpeed
		}
	var rotateSpeed: Double = 0.0
		set(inputSpeed) {
			applyDeadband(inputSpeed, IntakeConsts.ROTATE_DEAD_BAND)
			if(!rotateHolding) {
				rotationMotor.speed = inputSpeed
				field = inputSpeed
			}
		}

	init {
		reset()
	}

	private fun reset() {
		squeeze = SqueezeState.IN
	}

	fun autoReset() {
		// We have a cube at the start of auto
		cube = HasCube.TRUE
		reset()
	}

	fun teleopReset() {
		reset()
	}

	override fun initDefaultCommand() {
		ManualIntake(OI.XboxController2.getAxis(XboxConsts.Axis.LT), OI.XboxController2.getAxis(XboxConsts.Axis.RT))
	}
}

enum class SqueezeState(val solenoidState: DoubleSolenoid.Value) {
	IN(DoubleSolenoid.Value.kForward),
	OUT(DoubleSolenoid.Value.kReverse),
	NEUTRAL(DoubleSolenoid.Value.kOff);

	// Reverse lookup companion object
	companion object {
		// Create mapping from value (DoubleSolenoid.Value) to SqueezeState
		private val map =
				SqueezeState.values().associateBy(SqueezeState::solenoidState)

		// Function to return value from map
		fun fromInt(type: DoubleSolenoid.Value) =
			map[type] ?: throw IllegalArgumentException("Value not in SqueezeState enum")
	}
}

enum class HasCube {
	TRUE,
	FALSE,
	UNKNOWN
}
