package frc.team4096.robot.autonomous.modes

import frc.team4096.robot.autonomous.AutoMode
import frc.team4096.robot.drivetrain.DriveConsts
import frc.team4096.robot.drivetrain.commands.DriveDistanceCmd

object DriveForward : AutoMode() {
    override val pathDir = ""
    override val numPaths = 0

    override fun deserialize() {}

    init {
        addSequential(
                DriveDistanceCmd(
                        10.0,
                        DriveConsts.DT_MAX_ACCEL,
                        DriveConsts.DT_MAX_VEL,
                        DriveConsts.PIDVA_GAINS_PF
                )
        )
    }
}