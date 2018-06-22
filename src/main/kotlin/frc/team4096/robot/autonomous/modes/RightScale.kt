package frc.team4096.robot.autonomous.modes

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team4096.robot.autonomous.AutoMain
import frc.team4096.robot.subsystems.ElevatorSubsystem
import frc.team4096.robot.subsystems.IntakeSubsystem
import frc.team4096.robot.util.Commandify
import frc.team4096.robot.util.ElevatorConsts

object RightScale: CommandGroup() {
	init {
		// Raise elevator while moving
		addParallel(Commandify {
			ElevatorSubsystem.goMotionMagicDistance(ElevatorConsts.Positions.SCALE.pos)
		})

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
			Commandify { IntakeSubsystem.intakeSpeed = -0.75 },
			0.5
		)
	}
}