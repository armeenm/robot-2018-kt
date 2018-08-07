package frc.team4096.robot.autonomous.modes

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.util.commandify
import frc.team4096.robot.autonomous.AutoMain
import frc.team4096.robot.drivetrain.commands.FollowPathPFCmd
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.elevator.commands.AutoElevatorCmd
import frc.team4096.robot.intake.IntakeSubsystem

object LeftSwitch : CommandGroup() {
	var path: PFPath? = null

	init {
		// Follow spline for specific side
		when (AutoMain.autoData!![0]) {
			'L' -> PFPath("LS_L")
			'R' -> PFPath("LS_R")
			else -> println("Bad data!")
		}

		if (path != null) {
			// Raise elevator while moving
			addParallel(AutoElevatorCmd(ElevatorConsts.Positions.SWITCH.pos))

			// Follow path
			addSequential(FollowPathPFCmd(path!!))

			// Spit cube
			addSequential(commandify { IntakeSubsystem.intakeSpeed = -0.75 }, 0.5)
		}
	}
}