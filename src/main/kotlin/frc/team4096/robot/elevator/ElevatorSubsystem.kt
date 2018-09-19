package frc.team4096.robot.elevator

import com.ctre.phoenix.motorcontrol.ControlMode
import com.ctre.phoenix.motorcontrol.FeedbackDevice
import com.ctre.phoenix.motorcontrol.NeutralMode
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX
import edu.wpi.first.wpilibj.DoubleSolenoid
import frc.team4096.engine.extensions.configPIDF
import frc.team4096.engine.extensions.wpi.ZedSubsystem
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.robot.misc.MiscConsts

/**
 * Elevator subsystem.
 * Handles movement and braking.
 */
object ElevatorSubsystem : ZedSubsystem() {
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

    // Required Methods
    init {
        reset()
    }

    override fun reset() {
        // Configure Talon SRX (Master)
        masterMotor.apply {
            selectProfileSlot(ElevatorConsts.K_SLOT_ID, 0)

            configPIDF(
                    PIDVAVals(
                            ElevatorConsts.DISTANCE_kP,
                            ElevatorConsts.DISTANCE_kI,
                            ElevatorConsts.DISTANCE_kD,
                            ElevatorConsts.DISTANCE_kF
                    ),
                    ElevatorConsts.K_SLOT_ID
            )

            // Set sensor type
            configSelectedFeedbackSensor(
                    FeedbackDevice.CTRE_MagEncoder_Relative,
                    0,
                    MiscConsts.K_TIMEOUT_MS
            )

            // Reset encoder
            setSelectedSensorPosition(0, 0, MiscConsts.K_TIMEOUT_MS)

            configAllowableClosedloopError(
                    ElevatorConsts.K_SLOT_ID,
                    ElevatorConsts.MAX_CLOSED_LOOP_ERROR,
                    MiscConsts.K_TIMEOUT_MS
            )
            setStatusFramePeriod(
                    StatusFrameEnhanced.Status_13_Base_PIDF0,
                    10,
                    MiscConsts.K_TIMEOUT_MS
            )

            // Motion Magic
            setStatusFramePeriod(
                    StatusFrameEnhanced.Status_10_MotionMagic,
                    10,
                    MiscConsts.K_TIMEOUT_MS
            )
            configMotionAcceleration(ElevatorConsts.MAX_ACCEL, MiscConsts.K_TIMEOUT_MS)
            configMotionCruiseVelocity(ElevatorConsts.MAX_CRUISE_VEL, MiscConsts.K_TIMEOUT_MS)
            setNeutralMode(NeutralMode.Brake)

            // Voltage Compensation
            configVoltageCompSaturation(12.0, MiscConsts.K_TIMEOUT_MS)
            enableVoltageCompensation(true)
            /* Tweak vbus measurement filter.
             * Default is 32 cells in rolling average (1ms/sample)
             */
            configVoltageMeasurementFilter(32, 10)

            // Current Limiting
            // TODO: Figure out current limit constants
        }

        // Configure Victor SPX (Slave)
        slaveMotor.follow(masterMotor)
    }

    override fun log() {
        // TODO not implemented
    }

    // Set default command in OI
    override fun initDefaultCommand() {}

    override fun stop() {
        masterMotor.set(ControlMode.PercentOutput, 0.0)
        slaveMotor.set(ControlMode.PercentOutput, 0.0)
    }

    // Enums
    enum class ElevatorState(val solenoidState: DoubleSolenoid.Value) {
        FREE(DoubleSolenoid.Value.kReverse),
        HOLDING(DoubleSolenoid.Value.kForward),
        NEUTRAL(DoubleSolenoid.Value.kOff)
    }
}
