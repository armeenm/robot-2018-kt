package frc.team4096.robot.elevator.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.elevator.ElevatorSubsystem

/**
 * Manual elevator motion.
 * Intended for use with a joystick.
 * Does not finish but is interruptible.
 *
 * @param speed Speed to move elevator at
 */
class ManualElevatorCmd(private var speed: Double) : Command() {
	init {
		this.requires(ElevatorSubsystem)
		this.isInterruptible = true
	}

	override fun execute() {
		ElevatorSubsystem.speed =
			speed * ElevatorConsts.MAX_OPEN_LOOP_SPEED
	}

	override fun isFinished() = false

	override fun end() {
		ElevatorSubsystem.speed = 0.0
	}
}

