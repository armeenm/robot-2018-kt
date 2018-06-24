package frc.team4096.robot.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.robot.subsystems.IntakeSubsystem
import frc.team4096.robot.util.IntakeConsts

class ManualIntakeCmd(var inSpeed: Double, var outSpeed: Double): Command() {
	// Set the speed of the intake wheels to be the difference between the triggers
	// i.e. LT - RT
	override fun execute() {
		IntakeSubsystem.intakeSpeed =
			inSpeed * IntakeConsts.MAX_IN_SPEED - outSpeed * IntakeConsts.MAX_OUT_SPEED
	}

	override fun isFinished(): Boolean = false

	override fun end() { IntakeSubsystem.intakeSpeed = 0.0 }

	override fun interrupted() = this.end()
}
