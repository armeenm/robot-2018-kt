package frc.team4096.engine.extensions

import com.ctre.phoenix.motorcontrol.can.BaseMotorController
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.robot.misc.MiscConsts.K_TIMEOUT_MS

/**
 * Extension function to simplify CTRE motor controller PID configuration.
 *
 * @param pidfVals PIDF values to set
 * @param pidSlot Controller PID slot to use
 * @receiver CTRE motor controller, e.g. TalonSRX or VictorSPX
 */
fun BaseMotorController.configPIDF(pidfVals: PIDVAVals, pidSlot: Int = 0) {
	this.config_kP(pidSlot, pidfVals.kP, K_TIMEOUT_MS)
	this.config_kI(pidSlot, pidfVals.kI, K_TIMEOUT_MS)
	this.config_kD(pidSlot, pidfVals.kD, K_TIMEOUT_MS)
	this.config_kF(pidSlot, pidfVals.kF, K_TIMEOUT_MS)
}
