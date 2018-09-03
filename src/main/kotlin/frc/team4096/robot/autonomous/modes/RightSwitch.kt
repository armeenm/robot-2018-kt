package frc.team4096.robot.autonomous.modes

import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.util.commandify
import frc.team4096.robot.autonomous.AutoMode
import frc.team4096.robot.drivetrain.commands.FollowPathPFCmd
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.elevator.commands.AutoElevatorCmd
import frc.team4096.robot.intake.IntakeSubsystem

object RightSwitch : AutoMode() {
    override val pathDir = "R_SW"
    override val numPaths = 1
    var path: PFPath? = null

    init {
        pathMap
    }

    override fun setup(autoData: String) {
        path = pathMap[autoData[0]]!![0]

        if (path != null) {
            // Raise elevator while moving
            addParallel(AutoElevatorCmd(ElevatorConsts.Positions.SWITCH.pos))

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