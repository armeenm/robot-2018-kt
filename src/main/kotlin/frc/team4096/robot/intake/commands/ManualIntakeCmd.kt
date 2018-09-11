package frc.team4096.robot.intake.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.robot.intake.IntakeConsts
import frc.team4096.robot.intake.IntakeSubsystem

/**
 * Manual intake wheel rotation command.
 * Takes the difference between inSpeed and outSpeed.
 *
 * @param inSpeedFun Lambda to retrieve speed
 */
class ManualIntakeCmd(var speedFun: () -> Double) : Command() {
    init {
        requires(IntakeSubsystem)
        isInterruptible = true
    }

    override fun execute() {
        IntakeSubsystem.intakeSpeed = speedFun() * IntakeConsts.MAX_IN_SPEED
    }

    override fun isFinished(): Boolean = false

    override fun end() {
        IntakeSubsystem.intakeSpeed = 0.0
    }

    override fun interrupted() = this.end()
}
