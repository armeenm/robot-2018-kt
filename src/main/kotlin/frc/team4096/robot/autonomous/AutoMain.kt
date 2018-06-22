package frc.team4096.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser
import frc.team4096.robot.Robot
import frc.team4096.robot.autonomous.modes.*


object AutoMain {
	var autoData: String? = null
	var autoRun = false
	private val autoChooser = SendableChooser<CommandGroup>()

	init {
		autoChooser.addDefault(AutoMode.DO_NOTHING.modeStr, AutoMode.DO_NOTHING.cmdGroup)
		AutoMode.values().map { mode -> autoChooser.addObject(mode.modeStr, mode.cmdGroup) }
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