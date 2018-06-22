package frc.team4096.robot.util

import edu.wpi.first.wpilibj.command.Subsystem

// Custom subsystem with useful stuff
abstract class ZedSubsystem: Subsystem() {
	// Resets (Init)
	abstract fun reset()
	open fun autoReset() = reset()
	open fun teleopReset() = reset()

	// Logging
	abstract fun log()
	open fun autoLog() = log()
	open fun teleopLog() = log()
}