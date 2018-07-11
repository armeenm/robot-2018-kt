package frc.team4096.robot.sensors

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.engine.sensors.REVPressureSensor
import frc.team4096.robot.misc.MiscConsts

object PressureSensor : REVPressureSensor(MiscConsts.AIN_PRESSURE) {
	fun log() {
		SmartDashboard.putNumber("Pressure", PressureSensor.pressure)
	}
}