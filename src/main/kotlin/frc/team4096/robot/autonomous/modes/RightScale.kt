package frc.team4096.robot.autonomous.modes

import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.util.commandify
import frc.team4096.robot.autonomous.AutoMode
import frc.team4096.robot.drivetrain.commands.FollowPathPFCmd
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.elevator.commands.AutoElevatorCmd
import frc.team4096.robot.intake.IntakeSubsystem

object RightScale : AutoMode() {
    override val pathDir = "R_SC"
    override val numPaths = 1
    var path: PFPath? = null

    init {
        pathMap
    }

    override fun setup(autoData: String) {
        // Follow spline for specific side
        path = pathMap[autoData[1]]!![0]

        if (path != null) {
            // Raise elevator while moving
            addParallel(AutoElevatorCmd(ElevatorConsts.Positions.SCALE.pos))

            // Follow path
            addSequential(FollowPathPFCmd(path!!))

            // Spit cube
            addSequential(commandify { IntakeSubsystem.intakeSpeed = -0.75 }, 0.5)
        } else {
            // Drive forward otherwise
            addSequential(DriveForward)
        }
    }
}