@file:Suppress("unused")

package frc.team4096.robot

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.robot.subsystems.*
import frc.team4096.robot.util.*

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

		// Climber
		XboxController1.upDPad.whenPressed(Commandify {
			ClimberSubsystem.motor.speed = ClimberConsts.MAX_FORWARD_SPEED
		})
		XboxController1.upDPad.whenReleased(Commandify { ClimberSubsystem.motor.speed = 0.0 })

		// Controller 2 (Secondary Driver)
		// Elevator Setpoints
		XboxController2.downDPad.whenPressed(Commandify {
			ElevatorSubsystem.goMotionMagicDistance(ElevatorConsts.PositionList.BOTTOM.position)
		})
		XboxController2.rightDPad.whenPressed(Commandify {
			ElevatorSubsystem.goMotionMagicDistance(ElevatorConsts.PositionList.NO_DRAG.position)
		})
		XboxController2.leftDPad.whenPressed(Commandify {
			ElevatorSubsystem.goMotionMagicDistance(ElevatorConsts.PositionList.SWITCH.position)
		})
		XboxController2.upDPad.whenPressed(Commandify {
			ElevatorSubsystem.goMotionMagicDistance(ElevatorConsts.PositionList.SCALE.position)
		})

		// Rotation Motor
		XboxController2.yButton.whenPressed(Commandify {
			IntakeSubsystem.rotateHolding = false
			IntakeSubsystem.rotateSpeed = IntakeConsts.DEFAULT_ROTATE_SPEED
		})
		XboxController2.yButton.whenReleased(Commandify { IntakeSubsystem.rotateHolding = true })
		XboxController2.aButton.whenPressed(Commandify { IntakeSubsystem.rotateHolding = false })

		// Intake
		XboxController2.bButton.whenPressed(Commandify { IntakeSubsystem.squeeze = SqueezeState.OUT })
		XboxController2.bButton.whenPressed(Commandify { IntakeSubsystem.squeeze = SqueezeState.IN })

		// Climber
		XboxController2.startButton.whenPressed(Commandify { ClimberSubsystem.release() })
	}
}
