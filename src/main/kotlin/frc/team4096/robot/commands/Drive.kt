package frc.team4096.robot.commands

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.InstantCommand

import frc.team4096.robot.subsystems.DriveSubsystem
import frc.team4096.robot.subsystems.DriveSubsystem.GearState
import frc.team4096.robot.subsystems.DriveSubsystem.gear

class CurvatureDriveCmd(var xSpeed: Double, var zRotation: Double, var isQuickTurn: Boolean): Command() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = true
	}

	override fun execute() = DriveSubsystem.curvatureDrive(xSpeed, zRotation, isQuickTurn)

	override fun isFinished() = false

	override fun end() = DriveSubsystem.stop()

	override fun interrupted() = this.end()
}

class ToggleDriveGearCmd(): InstantCommand() {
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

// Uses a trapezoidal profile to drive a given distance
class DriveDistanceCmd(val distance: Double, val maxAccel: Double, val maxVel: Double): Command() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = false
	}

	override fun execute() {
		super.execute()
	}

	override fun isFinished(): Boolean {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}
