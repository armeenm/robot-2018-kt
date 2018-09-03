package frc.team4096.robot.autonomous.modes

import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.util.commandify
import frc.team4096.robot.autonomous.AutoMode
import frc.team4096.robot.drivetrain.commands.FollowPathPFCmd
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.elevator.commands.AutoElevatorCmd
import frc.team4096.robot.intake.IntakeSubsystem

/**
 * Autonomous mode to place a single cube in the switch, starting from the center.
 */
object CenterSwitch : AutoMode() {
    override val pathDir = "C_SW"
    override val numPaths = 1
    private var path: PFPath? = null

    init {
        pathMap
    }

    override fun setup(autoData: String) {
        // Get 0th element of data (our switch) and 0th element of path list (first path)
        path = pathMap[autoData[0]]!![0]

        if (path != null) {
            // Raise elevator while moving
            addParallel(AutoElevatorCmd(ElevatorConsts.Positions.SWITCH.pos))

            // Follow path
            addSequential(FollowPathPFCmd(path!!))

            // Spit cube
            addSequential(commandify { IntakeSubsystem.intakeSpeed = -0.75 }, 0.5)
        } else {
            // Drive forward if bad data
            addSequential(DriveForward)
        }
    }
}