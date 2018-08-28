package frc.team4096.robot.autonomous.modes

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.robot.drivetrain.DriveConsts
import frc.team4096.robot.drivetrain.commands.DriveDistanceCmd

object DriveForward : CommandGroup() {
    private val constraints = arrayOf(10.0, DriveConsts.kMaxVel, DriveConsts.kMaxAccel)
    // Educated guesses...
    val pidvaVals = PIDVAVals(
            kP = 0.5,
            kD = 0.2,
            kV = 0.15,
            kA = 0.298397119371985904928
    )

    init {
        addSequential(DriveDistanceCmd(10.0, DriveConsts.kMaxAccel, DriveConsts.kMaxVel, pidvaVals))
    }
}