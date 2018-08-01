package frc.team4096.robot.intake.commands

import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team4096.robot.intake.IntakeSubsystem
import frc.team4096.robot.intake.IntakeSubsystem.SqueezeState

class ToggleSqueezeCmd : InstantCommand() {
	init {
		this.requires(IntakeSubsystem)
		this.isInterruptible = false
	}

	override fun execute() {
		IntakeSubsystem.squeeze = when (IntakeSubsystem.squeeze) {
			SqueezeState.IN -> SqueezeState.OUT
			SqueezeState.OUT -> SqueezeState.IN
			SqueezeState.NEUTRAL -> {
				println("Shifting from neutral!")
				SqueezeState.IN
			}
		}
	}
}