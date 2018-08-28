package frc.team4096.robot.intake

import edu.wpi.first.wpilibj.DoubleSolenoid
import edu.wpi.first.wpilibj.SpeedControllerGroup
import edu.wpi.first.wpilibj.VictorSP
import frc.team4096.engine.extensions.wpi.ZedSubsystem
import frc.team4096.engine.motion.ControlState
import frc.team4096.engine.oi.XboxConsts
import frc.team4096.engine.util.applyDeadband
import frc.team4096.robot.intake.commands.ManualIntakeCmd
import frc.team4096.robot.misc.MiscConsts
import frc.team4096.robot.oi.OIMain

/**
 * Intake subsystem.
 * Handles spinning, rotation, and squeezing.
 */
object IntakeSubsystem : ZedSubsystem() {
    // Hardware
    // TODO: Add second motor controller for wheels
    private var wheelMotor1 = VictorSP(IntakeConsts.PWM_WHEELS_MOTOR_1)
    private var wheelMotor2 = VictorSP(IntakeConsts.PWM_WHEELS_MOTOR_2)
    private var wheelMotorGroup = SpeedControllerGroup(wheelMotor1, wheelMotor2)
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

    var rotateHolding = false
        set(input) {
            rotateSpeed = if (input) IntakeConsts.ROTATE_HOLD_SPEED else 0.0
            field = input
        }

    // Software States
    var wheelControlState = ControlState.OPEN_LOOP
    var rotationControlState = ControlState.OPEN_LOOP

    // Motor Values
    var intakeSpeed: Double = 0.0
        set(inputSpeed) {
            applyDeadband(inputSpeed, IntakeConsts.WHEEL_DEAD_BAND)
            wheelMotorGroup.set(inputSpeed)
            field = inputSpeed
        }
    var rotateSpeed: Double = 0.0
        set(inputSpeed) {
            applyDeadband(inputSpeed, IntakeConsts.ROTATE_DEAD_BAND)
            if (!rotateHolding) {
                rotationMotor.speed = inputSpeed
                field = inputSpeed
            }
        }

    // Required Methods
    init {
        reset()
    }

    override fun reset() {
        squeeze = SqueezeState.IN
    }

    override fun autoReset() {
        // We should have a cube at the start of auto
        cube = HasCube.TRUE
        reset()
    }

    override fun log() {}

    override fun stop() {}

    override fun initDefaultCommand() {}

    // Enums
    enum class SqueezeState(val solenoidState: DoubleSolenoid.Value) {
        IN(DoubleSolenoid.Value.kForward),
        OUT(DoubleSolenoid.Value.kReverse),
        NEUTRAL(DoubleSolenoid.Value.kOff);
    }

    enum class HasCube {
        TRUE,
        FALSE,
        UNKNOWN
    }
}


