package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.command.Command

/**
 * Curvature/Cheesy drive command for tele-op.
 * Does not finish but is interruptible.
 *
 * @param xSpeed Throttle joystick value
 * @param zRotation Yaw joystick value
 * @param isQuickTurn Allow for quick turning
 */
class CurvatureDriveCmd(var xSpeed: Double, var zRotation: Double, var isQuickTurn: Boolean) : Command() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = true
	}

	override fun execute() = DriveSubsystem.curvatureDrive(xSpeed, zRotation, isQuickTurn)

	override fun isFinished() = false

	override fun end() = DriveSubsystem.stop()

	override fun interrupted() = this.end()
}