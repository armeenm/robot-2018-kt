package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.motion.DriveSignal
import frc.team4096.robot.drivetrain.DriveSubsystem

/**
 * Default drive command.
 * Does not finish but is interruptible.
 *
 * @param xSpeed Throttle joystick value
 * @param zRotation Yaw joystick value
 * @param isQuickTurn Allow for quick turning
 */
class DriveCmd(val signal: DriveSignal) : Command() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = true
	}

	override fun execute() {
		DriveSubsystem.signal = signal
	}

	override fun isFinished() = false

	override fun end() = DriveSubsystem.stop()
}