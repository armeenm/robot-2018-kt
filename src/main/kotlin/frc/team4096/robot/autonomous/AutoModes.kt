package frc.team4096.robot.autonomous

import frc.team4096.robot.autonomous.modes.*

enum class AutoModes(val modeStr: String, val autoMode: AutoMode) {
    CENTER_SWITCH("Center to Switch", CenterSwitch),
    RIGHT_SWITCH("Right to Switch", RightSwitch),
    LEFT_SWITCH("Left to Switch", LeftSwitch),
    RIGHT_SCALE("Right to Scale", RightScale),
    LEFT_SCALE("Left to Scale", LeftScale),
    DRIVE_FORWARD("Drive Forward", DriveForward),
    DO_NOTHING("Do Nothing", DoNothing)
}
