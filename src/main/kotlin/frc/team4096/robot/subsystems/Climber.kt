package frc.team4096.robot.subsystems

import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj.VictorSP
import frc.team4096.robot.util.ClimberConsts
import frc.team4096.engine.wpi.ZedSubsystem

/**
 * Climber subsystem.
 * Handles climber motor and release servo.
 */
object ClimberSubsystem: ZedSubsystem() {
	// Hardware
	var motor = VictorSP(ClimberConsts.PWM_MOTOR)
	private var servo = Servo(ClimberConsts.PWM_SERVO)

	// Hardware States
	var isReleased = false

	// Required Methods
	init { reset() }

	override fun reset() {
		isReleased = false
		servo.angle = 0.0
	}

	override fun log() {

	}

	override fun initDefaultCommand() {}

	/**
	 * Release climbing apparatus.
	 */
	fun release() {
		isReleased = true
		servo.angle = ClimberConsts.SERVO_RELEASE_ANGLE
	}
}