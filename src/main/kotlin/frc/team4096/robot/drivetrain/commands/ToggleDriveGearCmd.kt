package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.InstantCommand
import frc.team4096.robot.drivetrain.DriveSubsystem
import frc.team4096.robot.drivetrain.GearState

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
            GearState.HIGH -> GearState.LOW
            GearState.LOW -> GearState.HIGH
            GearState.NEUTRAL -> {
                println("Shifting from neutral!")
                GearState.HIGH
            }
        }
    }
}