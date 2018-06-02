package frc.team4096.robot

import edu.wpi.first.wpilibj.ADXRS450_Gyro
import edu.wpi.first.wpilibj.TimedRobot
import frc.team4096.robot.subsystems.ClimberSubsystem
import frc.team4096.robot.subsystems.DriveSubsystem
import frc.team4096.robot.subsystems.ElevatorSubsystem
import frc.team4096.robot.subsystems.IntakeSubsystem

object Robot: TimedRobot() {
	// Sensors
	val gyro = ADXRS450_Gyro()
	val subsystemList = listOf(DriveSubsystem, IntakeSubsystem, ElevatorSubsystem, ClimberSubsystem)

	override fun robotInit() {
		// Will run init() function in each subsystem
		subsystemList
	}

	override fun disabledInit() { }

	override fun disabledPeriodic() { }

	override fun autonomousInit() {
		// TODO: Mappy stuff!
	}

	override fun autonomousPeriodic() { }

	override fun teleopInit() { }

	override fun teleopPeriodic() { }

	override fun testInit() { }

	override fun testPeriodic() { }
}
