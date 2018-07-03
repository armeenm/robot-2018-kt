package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.command.InstantCommand

/**
 * Toggle the drive gear state
 */
class ToggleDriveGearCmd() : InstantCommand() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = false
	}

	override fun execute() {
		DriveSubsystem.gear = when (DriveSubsystem.gear) {
			DriveSubsystem.GearState.HIGH -> {
				DriveSubsystem.GearState.LOW
			}
			DriveSubsystem.GearState.LOW -> DriveSubsystem.GearState.HIGH
			DriveSubsystem.GearState.NEUTRAL -> {
				println("Shifting from neutral!")
				DriveSubsystem.GearState.HIGH
			}
		}
	}
}