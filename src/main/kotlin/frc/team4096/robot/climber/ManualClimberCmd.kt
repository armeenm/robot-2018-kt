package frc.team4096.robot.climber

import edu.wpi.first.wpilibj.command.Command

class ManualClimberCmd(val speed: Double) : Command() {
	override fun execute() {
		ClimberSubsystem.speed = speed
	}

	override fun isFinished() = false

	override fun end() {
		ClimberSubsystem.speed = 0.0
	}
}