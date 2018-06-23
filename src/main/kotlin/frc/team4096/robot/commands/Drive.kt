package frc.team4096.robot.commands

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.InstantCommand

import frc.team4096.robot.subsystems.DriveSubsystem
import frc.team4096.robot.subsystems.DriveSubsystem.GearState
import frc.team4096.robot.subsystems.DriveSubsystem.gear

class CurvatureDrive(var xSpeed: Double, var zRotation: Double, var isQuickTurn: Boolean): Command() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = true
	}

	override fun execute() = DriveSubsystem.curvatureDrive(xSpeed, zRotation, isQuickTurn)

	override fun isFinished() = false

	override fun end() = DriveSubsystem.stop()

	override fun interrupted() = this.end()
}

class ToggleDriveGearState(): InstantCommand() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = false
	}

	override fun execute() {
		gear = when (gear) {
			GearState.HIGH -> {
				GearState.LOW
			}
			GearState.LOW -> GearState.HIGH
			GearState.NEUTRAL -> {
				println("Shifting from neutral!")
				GearState.HIGH
			}
		}
	}
}
