package frc.team4096.robot.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.robot.subsystems.ElevatorSubsystem
import frc.team4096.robot.util.ElevatorConsts

class ManualElevator(var speed: Double): Command() {
	override fun execute() {
		ElevatorSubsystem.speed =
			speed * ElevatorConsts.MAX_OPEN_LOOP_SPEED
	}

	override fun isFinished(): Boolean = false

	override fun end() { ElevatorSubsystem.speed = 0.0 }

	override fun interrupted() = this.end()
}
