package frc.team4096.robot.subsystems

import edu.wpi.first.wpilibj.Servo
import edu.wpi.first.wpilibj.VictorSP
import edu.wpi.first.wpilibj.command.Subsystem
import frc.team4096.robot.util.ClimberConsts
import frc.team4096.robot.util.Commandify

object ClimberSubsystem: Subsystem() {
	// Hardware
	var motor = VictorSP(ClimberConsts.PWM_MOTOR)
	private var servo = Servo(ClimberConsts.PWM_SERVO)

	// Hardware States
	var isReleased = false

	init { reset() }

	fun reset() {
		isReleased = false
		servo.angle = 0.0
	}

	override fun initDefaultCommand() {}

	fun release() {
		isReleased = true
		servo.angle = ClimberConsts.SERVO_RELEASE_ANGLE
	}
}