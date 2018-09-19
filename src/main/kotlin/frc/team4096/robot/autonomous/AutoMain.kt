package frc.team4096.robot.autonomous

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.robot.autonomous.modes.CenterSwitch
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
        CenterSwitch
        autoChooser.addDefault(AutoModes.CENTER_SWITCH.modeStr, AutoModes.CENTER_SWITCH.autoMode)
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
        if (autoData == null) {
            fetchData()
        } else {
            println("Auto Data: $autoData")
            hasAutoRun = true
            autoChooser.selected.apply {
                setup(autoData!!)
                start()
            }
        }
    }
}
