package frc.team4096.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.robot.Robot
import frc.team4096.robot.autonomous.modes.*

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
		AutoMode.values().forEach { mode -> autoChooser.addObject(mode.modeStr, mode.cmdGroup) }
		SmartDashboard.putData(autoChooser)
	}

	fun fetchData() { autoData = Robot.driverStation.gameSpecificMessage }

	fun runAuto() {
		if (autoData == null)
			fetchData()
		else {
			autoChooser.selected.start()
			autoRun = true
		}
	}
}

enum class AutoMode(val modeStr: String, val cmdGroup: CommandGroup) {
	CENTER_SWITCH("Center to Switch", CenterSwitch),
	RIGHT_SWITCH("Right to Switch", RightSwitch),
	LEFT_SWITCH("Left to Switch", LeftSwitch),
	RIGHT_SCALE("Right to Scale", RightScale),
	LEFT_SCALE("Left to Scale", LeftScale),
	DRIVE_FORWARD("Drive Forward", DriveForward),
	DO_NOTHING("Do Nothing", DoNothing)
}
