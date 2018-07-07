package frc.team4096.engine.sensors

import edu.wpi.first.wpilibj.AnalogInput
import frc.team4096.robot.misc.MiscConsts

/**
 * REV Robotics pressure sensor class.
 *
 * @param analogPin Analog input pin
 * @param vCC Supply voltage, defaults to 5V
 */
open class REVPressureSensor(val analogPin: Int, val vCC: Double = 5.0) {
	val analogInput = AnalogInput(analogPin)

	init {
		analogInput.averageBits = MiscConsts.PRESSURE_AVG_BITS
	}

	val pressure: Double
		/**
		 * Convert analog pressure sensor values (V) to pressure (PSI).
		 * Equation from REV Robotics part datasheet (REV-11-1107-DS-00).
		 */
		get() = 250 * analogInput.averageVoltage / vCC - 25
}
