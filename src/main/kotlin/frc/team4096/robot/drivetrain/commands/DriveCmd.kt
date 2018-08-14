package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.motion.DriveSignal
import frc.team4096.robot.drivetrain.DriveSubsystem

/**
 * Default drive command.
 * Does not finish but is interruptible.
 *
 * @param signalFun Lambda to retrieve DriveSignal
 */
class DriveCmd(private val signalFun: () -> DriveSignal) : Command() {
    init {
        this.requires(DriveSubsystem)
        this.isInterruptible = true
    }

    override fun execute() {
        DriveSubsystem.signal = signalFun()
    }

    override fun isFinished() = false

    override fun end() = DriveSubsystem.stop()
}