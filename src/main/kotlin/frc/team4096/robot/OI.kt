@file:Suppress("unused")

package frc.team4096.robot

import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.JoystickButton
import frc.team4096.robot.subsystems.DriveSubsystem
import frc.team4096.robot.subsystems.GearState
import frc.team4096.robot.util.Commandify

import frc.team4096.robot.util.XboxConsts
import frc.team4096.robot.util.Z_XboxController
import frc.team4096.robot.util.applyDeadband

object OI {

	val XboxController1 =  Z_XboxController(0)
	val XboxController2 =  Z_XboxController(1)

	init {
		// Controller 1 (Main Driver)
		// Drivetrain
		XboxController1.rbButton.whenPressed(Commandify { DriveSubsystem.setGearState(GearState.LOW) })
		XboxController1.rbButton.whenReleased(Commandify { DriveSubsystem.setGearState(GearState.HIGH) })
	}

	fun getAxis(controller: XboxController, axis: XboxConsts.Axis, inverted: Boolean = false) =
		applyDeadband(controller.getRawAxis(axis.id) * if (inverted) -1 else 1, XboxConsts.DEAD_BAND)
}
