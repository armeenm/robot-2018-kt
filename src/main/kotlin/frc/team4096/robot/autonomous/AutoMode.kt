package frc.team4096.robot.autonomous

import edu.wpi.first.wpilibj.command.CommandGroup
import frc.team4096.robot.autonomous.modes.*

enum class AutoMode(val modeStr: String, val cmdGroup: CommandGroup) {
	CENTER_SWITCH("Center to Switch", CenterSwitch),
	RIGHT_SWITCH("Right to Switch", RightSwitch),
	LEFT_SWITCH("Left to Switch", LeftSwitch),
	RIGHT_SCALE("Right to Scale", RightScale),
	LEFT_SCALE("Left to Scale", LeftScale),
	DRIVE_FORWARD("Drive Forward", DriveForward),
	DO_NOTHING("Do Nothing", DoNothing)
}
