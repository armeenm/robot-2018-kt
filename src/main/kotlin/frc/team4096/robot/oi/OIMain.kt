@file:Suppress("unused")

package frc.team4096.robot.oi

import frc.team4096.engine.oi.ZedXboxController
import frc.team4096.engine.util.commandify
import frc.team4096.robot.climber.ClimberConsts
import frc.team4096.robot.climber.ClimberSubsystem
import frc.team4096.robot.elevator.AutoElevatorCmd
import frc.team4096.robot.drivetrain.DriveSubsystem
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.intake.IntakeConsts
import frc.team4096.robot.intake.IntakeSubsystem

/**
 * Operator input object.
 * Handles all joystick activity.
 */
object OIMain {
	// Setup controllers with custom Ctrl-Z XboxController wrapper
	val XboxController1 = ZedXboxController(0)
	val XboxController2 = ZedXboxController(1)

	init {
		// Controller 1 (Main Driver)

		// Drivetrain
		// Shifting
		XboxController1.rbButton.whenPressed(commandify { DriveSubsystem.gear = DriveSubsystem.GearState.LOW })
		XboxController1.rbButton.whenReleased(commandify { DriveSubsystem.gear = DriveSubsystem.GearState.HIGH })

		// Climber
		XboxController1.upDPad.whenPressed(commandify {
			ClimberSubsystem.motor.speed = ClimberConsts.MAX_FORWARD_SPEED
		})
		XboxController1.upDPad.whenReleased(commandify { ClimberSubsystem.motor.speed = 0.0 })

		// Controller 2 (Secondary Driver)
		// Elevator Setpoints
		XboxController2.downDPad.whenPressed(
			AutoElevatorCmd(ElevatorConsts.Positions.BOTTOM.pos)
		)
		XboxController2.rightDPad.whenPressed(
			AutoElevatorCmd(ElevatorConsts.Positions.NO_DRAG.pos)
		)
		XboxController2.leftDPad.whenPressed(
			AutoElevatorCmd(ElevatorConsts.Positions.SWITCH.pos)
		)
		XboxController2.upDPad.whenPressed(
			AutoElevatorCmd(ElevatorConsts.Positions.SCALE.pos)
		)

		// Rotation Motor
		XboxController2.yButton.whenPressed(commandify {
			IntakeSubsystem.rotateHolding = false
			IntakeSubsystem.rotateSpeed = IntakeConsts.DEFAULT_ROTATE_SPEED
		})
		XboxController2.yButton.whenReleased(commandify { IntakeSubsystem.rotateHolding = true })
		XboxController2.aButton.whenPressed(commandify { IntakeSubsystem.rotateHolding = false })

		// Intake
		XboxController2.bButton.whenPressed(commandify { IntakeSubsystem.squeeze = IntakeSubsystem.SqueezeState.OUT })
		XboxController2.bButton.whenPressed(commandify { IntakeSubsystem.squeeze = IntakeSubsystem.SqueezeState.IN })

		// Climber
		XboxController2.selectButton.whenPressed(commandify { ClimberSubsystem.release() })
	}
}
