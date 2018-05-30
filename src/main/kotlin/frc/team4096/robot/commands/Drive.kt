package frc.team4096.robot.commands

import edu.wpi.first.wpilibj.command.Command

import frc.team4096.robot.subsystems.DriveSubsystem

class CurvatureDrive(var xSpeed: Double, var zRotation: Double, var isQuickTurn: Boolean): Command() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = true
	}

	override fun initialize() {
		DriveSubsystem.isQuickTurn = isQuickTurn
	}

	override fun execute() = DriveSubsystem.curvatureDrive(xSpeed, zRotation, isQuickTurn)

	override fun isFinished() = false

	override fun end() = DriveSubsystem.stop()

	override fun interrupted() = this.end()
}
