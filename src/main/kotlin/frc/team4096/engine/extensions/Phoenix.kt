package frc.team4096.engine.extensions

import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import frc.team4096.engine.motion.util.PIDFVals
import frc.team4096.robot.util.MiscConsts.K_TIMEOUT_MS

// An extension to simplify PID configuration for a Talon SRX or Victor SPX.
fun BaseMotorController.configPIDF(pidfVals: PIDFVals, pidSlot: Int = 0) {
	this.config_kP(pidSlot, pidfVals.kP, K_TIMEOUT_MS)
	this.config_kI(pidSlot, pidfVals.kI, K_TIMEOUT_MS)
	this.config_kD(pidSlot, pidfVals.kD, K_TIMEOUT_MS)
	this.config_kF(pidSlot, pidfVals.kF, K_TIMEOUT_MS)
}
