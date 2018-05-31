@file:Suppress("unused")

package frc.team4096.robot

import frc.team4096.robot.subsystems.DriveSubsystem
import frc.team4096.robot.subsystems.GearState
import frc.team4096.robot.subsystems.IntakeSubsystem
import frc.team4096.robot.util.Commandify
import frc.team4096.robot.util.IntakeConsts
import frc.team4096.robot.util.Z_XboxController

object OI {

	// Setup controllers with custom Ctrl-"Z" XboxController wrapper
	val XboxController1 =  Z_XboxController(0)
	val XboxController2 =  Z_XboxController(1)

	init {
		// Controller 1 (Main Driver)

		// Drivetrain
		// Shifting
		XboxController1.rbButton.whenPressed(Commandify { DriveSubsystem.gear = GearState.LOW })
		XboxController1.rbButton.whenReleased(Commandify { DriveSubsystem.gear = GearState.HIGH })

		// Controller 2 (Secondary Driver)
		// Rotation Motor
		XboxController2.yButton.whenPressed(Commandify {
			IntakeSubsystem.rotateHolding = false
			IntakeSubsystem.rotateSpeed = IntakeConsts.DEFAULT_ROTATE_SPEED
		})
		XboxController2.yButton.whenReleased(Commandify { IntakeSubsystem.rotateHolding = true })

		XboxController2.aButton.whenPressed(Commandify { IntakeSubsystem.rotateHolding = false })
	}
}
