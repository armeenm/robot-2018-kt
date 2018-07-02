package frc.team4096.robot

import edu.wpi.first.wpilibj.*
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.robot.autonomous.AutoMain
import frc.team4096.robot.climber.ClimberSubsystem
import frc.team4096.robot.drivetrain.DriveSubsystem
import frc.team4096.robot.elevator.ElevatorSubsystem
import frc.team4096.robot.intake.IntakeSubsystem
import frc.team4096.robot.misc.MiscConsts

/**
 * Main robot class, instantiated by WPILib.
 * Inherits from timed robot for consistent frequency.
 */
class Robot: TimedRobot() {
	companion object {
		// Sensors
		val gyro = ADXRS450_Gyro()
		// Note: use the averaged value from the pressure sensor
		private val pressureSensor = AnalogInput(MiscConsts.AIN_PRESSURE)

		private val cameraServer = CameraServer.getInstance()

		val driverStation: DriverStation = DriverStation.getInstance()
		val scheduler: Scheduler = Scheduler.getInstance()

		private val subsystemList = listOf(DriveSubsystem, IntakeSubsystem, ElevatorSubsystem, ClimberSubsystem)
	}

	override fun robotInit() {
		// Put all of the subsystems on SmartDashboard
		subsystemList.forEach{ SmartDashboard.putData(it) }

		// Miscellaneous setups
		gyro.reset()

		pressureSensor.averageBits = MiscConsts.PRESSURE_AVG_BITS

		cameraServer.startAutomaticCapture()
	}

	// DISABLED //
	override fun disabledInit() {
		// Empty out the scheduler
		scheduler.removeAll()
	}

	override fun disabledPeriodic() { }

	// ENABLED //
	fun periodic() {
		scheduler.run()
	}

	override fun robotPeriodic() {
		periodic()
	}

	// AUTONOMOUS //
	override fun autonomousInit() {
		// Reset all subsystems for autonomous
		subsystemList.forEach{ it.autoReset() }

		AutoMain.fetchData()
	}

	override fun autonomousPeriodic() {
		periodic()
		AutoMain.runAuto()
	}

	// TELE-OPERATED //
	override fun teleopInit() {
		// Clear out scheduler, potentially from autonomous
		Scheduler.getInstance().removeAll()
		// Reset all subsystems for teleop
		subsystemList.forEach{ it.teleopReset() }
	}

	override fun teleopPeriodic() { }

	// TEST //
	override fun testInit() { }

	override fun testPeriodic() { }
}
