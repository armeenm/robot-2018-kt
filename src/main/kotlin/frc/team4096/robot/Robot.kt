package frc.team4096.robot

import edu.wpi.first.wpilibj.TimedRobot

import frc.team4096.robot.subsystems.DriveSubsystem
import frc.team4096.robot.subsystems.IntakeSubsystem

object Robot: TimedRobot() {

	override fun robotInit() {
		// Initialize subsystems
		DriveSubsystem
		IntakeSubsystem
	}

	override fun disabledInit() { }

	override fun disabledPeriodic() { }

	override fun autonomousInit() { }

	override fun autonomousPeriodic() { }

	override fun teleopInit() { }

	override fun teleopPeriodic() { }

	override fun testInit() { }

	override fun testPeriodic() { }
}
