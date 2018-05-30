package frc.team4096.robot

import edu.wpi.first.wpilibj.TimedRobot

import frc.team4096.robot.subsystems.DriveSubsystem

public class Robot : TimedRobot() {

	override fun robotInit() {
		// Initialize subsystems
		DriveSubsystem
	}

	override fun disabledInit() { }

	override fun autonomousInit() { }

	override fun teleopInit() { }

	override fun testInit() { }

	override fun disabledPeriodic() { }

	override fun autonomousPeriodic() { }

	override fun teleopPeriodic() { }

	override fun testPeriodic() { }
}
