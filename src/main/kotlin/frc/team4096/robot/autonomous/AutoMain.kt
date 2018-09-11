package frc.team4096.robot.autonomous

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.engine.motion.PFPath
import frc.team4096.robot.misc.driverStation

/**
 * Main autonomous handler.
 * Handles calling the correct mode, game data, etc.
 */
object AutoMain {
    var autoData: String? = null
    var hasAutoRun = false
    val autoChooser = SendableChooser<AutoMode>()

    init {
        autoChooser.addDefault(AutoModes.DO_NOTHING.modeStr, AutoModes.DO_NOTHING.autoMode)
        AutoModes.values().forEach { autoChooser.addObject(it.modeStr, it.autoMode) }
        SmartDashboard.putData(autoChooser)
    }

    fun deserialize() {
        AutoModes.values().forEach { it.autoMode.deserialize() }
    }

    fun fetchData() {
        autoData = driverStation.gameSpecificMessage
    }

    fun runAuto() {
        if (autoData == null)
            fetchData()
        else {
            autoChooser.selected.apply {
                setup(autoData!!)
                start()
            }
            hasAutoRun = true
        }
    }
}
