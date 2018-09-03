package frc.team4096.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team4096.engine.motion.PFPath

abstract class AutoMode : CommandGroup() {
    abstract val pathDir: String
    abstract val numPaths: Int
    /*
    open val pathMap: HashMap<Char, List<PFPath>> by lazy {
        hashMapOf<Char, List<PFPath>>().also {
            for (side in listOf('L', 'R')) {
                it.put(
                        side,
                        mutableListOf<PFPath>().also {
                            for (pathNum in 1..numPaths) {
                                it.add(PFPath("$pathDir/$side/$pathNum"))
                            }
                        }
                )
            }
        }
    }
    */
    open val pathMap: HashMap<Char, List<PFPath>> by lazy {
        hashMapOf<Char, List<PFPath>> {
            listOf('L', 'R').forEach { side ->
                side to List(numPaths) { i ->
                    PFPath("$pathDir/$side/$i")
                }
            }
        }
    }

    open fun deserialize() = pathMap.forEach { it.value.forEach { it.deserialize() } }
    open fun setup(autoData: String) {}
}