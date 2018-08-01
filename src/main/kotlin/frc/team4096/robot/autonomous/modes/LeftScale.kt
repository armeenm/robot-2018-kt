package frc.team4096.robot.autonomous.modes

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team4096.engine.util.commandify
import frc.team4096.robot.autonomous.AutoMain
import frc.team4096.robot.elevator.commands.AutoElevatorCmd
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.intake.IntakeSubsystem

object LeftScale : CommandGroup() {
	init {
		// Raise elevator while moving
		addParallel(
			AutoElevatorCmd(ElevatorConsts.Positions.SCALE.pos)
		)

		// Follow spline for specific side
		when (AutoMain.autoData!![0]) {
			'L' -> {
				println("Do PF stuff here")
			}
			'R' -> {
				println("Do PF stuff here")
			}

			else -> println("Bad data!")
		}

		// Spit cube
		addSequential(
			commandify { IntakeSubsystem.intakeSpeed = -0.75 },
			0.5
		)
	}
}