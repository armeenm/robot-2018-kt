package frc.team4096.robot

import edu.wpi.first.wpilibj.ADXRS450_Gyro
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.networktables.NetworkTable
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.robot.subsystems.*

object Robot: TimedRobot() {
	// Sensors
	val gyro = ADXRS450_Gyro()

	private val subsystemList = listOf(DriveSubsystem, IntakeSubsystem, ElevatorSubsystem, ClimberSubsystem)

	override fun robotInit() {
		// Will implicitly run init method in each subsystem
		subsystemList

		subsystemList.map({ subsystem -> SmartDashboard.putData(subsystem) })

		gyro.reset()
	}

	override fun disabledInit() {
		// Empty out the scheduler on disable
		Scheduler.getInstance().removeAll()
	}

	override fun disabledPeriodic() { }

	override fun autonomousInit() {
		// Reset all subsystems for autonomous
		subsystemList.map({ subsystem -> subsystem.autoReset() })
	}

	override fun autonomousPeriodic() { }

	override fun teleopInit() {
		// Reset all subsystems for teleop
		subsystemList.map({ it -> it.teleopReset() })
	}

	override fun teleopPeriodic() { }

	override fun testInit() { }

	override fun testPeriodic() { }
}
