package frc.team4096.robot.intake.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.robot.intake.IntakeConsts
import frc.team4096.robot.intake.IntakeSubsystem

/**
 * Manual intake wheel rotation command.
 * Takes the difference between inSpeed and outSpeed.
 *
 * @param inSpeed Inward speed
 * @param outSpeed Outward speed
 */
class ManualIntakeCmd(var inSpeed: Double, var outSpeed: Double) : Command() {
	override fun execute() {
		IntakeSubsystem.intakeSpeed =
			inSpeed * IntakeConsts.MAX_IN_SPEED - outSpeed * IntakeConsts.MAX_OUT_SPEED
	}

	override fun isFinished(): Boolean = false

	override fun end() {
		IntakeSubsystem.intakeSpeed = 0.0
	}

	override fun interrupted() = this.end()
}
