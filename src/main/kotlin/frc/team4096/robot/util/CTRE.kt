package frc.team4096.robot.util

import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import frc.team4096.robot.util.MiscConsts.K_TIMEOUT_MS

// An extension to simplify PID configuration for a Talon SRX or Victor SPX.
fun BaseMotorController.configPIDF(kP: Double, kI: Double, kD: Double, kF: Double = 0.0, pidSlot: Int = 0) {
	this.config_kP(pidSlot, kP, K_TIMEOUT_MS)
	this.config_kI(pidSlot, kI, K_TIMEOUT_MS)
	this.config_kD(pidSlot, kD, K_TIMEOUT_MS)
	this.config_kF(pidSlot, kF, K_TIMEOUT_MS)
}
