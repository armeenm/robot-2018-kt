package frc.team4096.engine.extensions.wpi

import edu.wpi.first.wpilibj.command.Subsystem

// Custom subsystem with useful stuff
/**
 * Custom subsystem that introduces useful features.
 * Only logging and resets for now.
 */
abstract class ZedSubsystem(freq: Double = 50.0) : Subsystem() {
    abstract fun reset()

    open fun autoReset() = reset()
    open fun teleopReset() = reset()

    abstract fun log()

    open fun autoLog() = log()
    open fun teleopLog() = log()

    abstract fun stop()

    override fun initDefaultCommand() {}
}