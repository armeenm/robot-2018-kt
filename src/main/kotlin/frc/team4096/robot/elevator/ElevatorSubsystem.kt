package frc.team4096.robot.elevator

import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.DoubleSolenoid
import frc.team4096.engine.extensions.configPIDF
import frc.team4096.engine.motion.util.ControlState
import frc.team4096.engine.motion.util.PIDFVals
import frc.team4096.engine.wpi.ZedSubsystem
import frc.team4096.robot.oi.OIMain
import frc.team4096.robot.misc.*

/**
 * Elevator subsystem.
 * Handles movement and braking.
 */
object ElevatorSubsystem: ZedSubsystem() {
	// Hardware
	var masterMotor = WPI_TalonSRX(ElevatorConsts.CAN_MASTER_MOTOR)
	var slaveMotor = WPI_VictorSPX(ElevatorConsts.CAN_SLAVE_MOTOR)

	private var brakeSolenoid = DoubleSolenoid(
			MiscConsts.CAN_PCM,
			ElevatorConsts.PCM_BRAKE_1,
			ElevatorConsts.PCM_BRAKE_2
	)

	// Hardware States
	var hwState = ElevatorState.FREE
		set(inputState) {
			brakeSolenoid.set(inputState.solenoidState)
			field = inputState
		}
	var speed = 0.0
		set(inputSpeed) {
			masterMotor.set(inputSpeed)
			field = inputSpeed
		}

	// Software States
	var controlState = ControlState.OPEN_LOOP

	// Required Methods
	init {
		reset()
	}

	override fun reset() {
		// Configure Talon SRX (Master)
		masterMotor.selectProfileSlot(ElevatorConsts.K_SLOT_ID, 0)
		masterMotor.configPIDF(
			PIDFVals(
				ElevatorConsts.DISTANCE_kP,
				ElevatorConsts.DISTANCE_kI,
				ElevatorConsts.DISTANCE_kD,
				ElevatorConsts.DISTANCE_kF
			),
			ElevatorConsts.K_SLOT_ID
		)
		masterMotor.configSelectedFeedbackSensor(
				FeedbackDevice.CTRE_MagEncoder_Relative,
				0,
				MiscConsts.K_TIMEOUT_MS
		)

		masterMotor.setSelectedSensorPosition(0, 0, MiscConsts.K_TIMEOUT_MS)

		masterMotor.configAllowableClosedloopError(
				ElevatorConsts.K_SLOT_ID,
				ElevatorConsts.MAX_CLOSED_LOOP_ERROR,
				MiscConsts.K_TIMEOUT_MS
		)

		masterMotor.setStatusFramePeriod(
				StatusFrameEnhanced.Status_13_Base_PIDF0,
				10,
				MiscConsts.K_TIMEOUT_MS
		)
		masterMotor.setStatusFramePeriod(
				StatusFrameEnhanced.Status_10_MotionMagic,
				10,
				MiscConsts.K_TIMEOUT_MS
		)

		masterMotor.configMotionAcceleration(ElevatorConsts.MAX_ACCEL, MiscConsts.K_TIMEOUT_MS)
		masterMotor.configMotionCruiseVelocity(ElevatorConsts.MAX_CRUISE_VEL, MiscConsts.K_TIMEOUT_MS)

		masterMotor.setNeutralMode(NeutralMode.Brake)

		// Configure Victor SPX (Slave)
		slaveMotor.follow(masterMotor)
	}

	override fun log() {
		TODO("not implemented")
	}

	override fun initDefaultCommand() {
		ManualElevatorCmd(OIMain.XboxController2.getAxis(XboxConsts.Axis.LEFT_Y))
	}

	// Enums
	enum class ElevatorState(val solenoidState: DoubleSolenoid.Value) {
		FREE(DoubleSolenoid.Value.kReverse),
		HOLDING(DoubleSolenoid.Value.kForward),
		NEUTRAL(DoubleSolenoid.Value.kOff)
	}
}
