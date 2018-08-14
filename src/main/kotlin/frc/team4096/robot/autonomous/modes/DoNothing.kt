package frc.team4096.robot.autonomous.modes

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team4096.robot.misc.MiscConsts.RAP_LYRICS

object DoNothing : CommandGroup() {
    init {
        println(RAP_LYRICS)
    }
}