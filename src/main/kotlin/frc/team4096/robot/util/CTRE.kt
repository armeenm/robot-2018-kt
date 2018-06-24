package frc.team4096.robot.util

import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import frc.team4096.robot.util.MiscConsts.K_TIMEOUT_MS

// An extension to simplify PID configuration for a Talon SRX or Victor SPX.
fun BaseMotorController.configPIDF(pidVals: PIDVals, pidSlot: Int = 0) {
	this()
			.config_kP(pidSlot, pidVals.kP, K_TIMEOUT_MS)
			.config_kI(pidSlot, pidVals.kI, K_TIMEOUT_MS)
			.config_kD(pidSlot, pidVals.kD, K_TIMEOUT_MS)
			.config_kF(pidSlot, pidVals.kF, K_TIMEOUT_MS)
}
