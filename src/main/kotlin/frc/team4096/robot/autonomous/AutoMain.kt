package frc.team4096.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.robot.Robot

/**
 * Main autonomous handler.
 * Handles calling the correct mode, game data, etc.
 */
object AutoMain {
	var autoData: String? = null
	var autoRun = false
	private val autoChooser = SendableChooser<CommandGroup>()

	init {
		autoChooser.addDefault(AutoMode.DO_NOTHING.modeStr, AutoMode.DO_NOTHING.cmdGroup)
		AutoMode.values().forEach { autoChooser.addObject(it.modeStr, it.cmdGroup) }
		SmartDashboard.putData(autoChooser)
	}

	fun fetchData() {
		autoData = Robot.driverStation.gameSpecificMessage
	}

	fun runAuto() {
		if (autoData == null)
			fetchData()
		else {
			autoChooser.selected.start()
			autoRun = true
		}
	}
}
