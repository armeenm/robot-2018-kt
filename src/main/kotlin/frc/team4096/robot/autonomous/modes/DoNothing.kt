package frc.team4096.robot.autonomous.modes

import frc.team4096.engine.util.commandify
import frc.team4096.robot.autonomous.AutoMode
import frc.team4096.robot.misc.MiscConsts.RAP_LYRICS

object DoNothing : AutoMode() {
    override val pathDir = ""
    override val numPaths = 0

    override fun deserialize() {}

    init {
        addSequential(commandify { println(RAP_LYRICS) })
    }
}