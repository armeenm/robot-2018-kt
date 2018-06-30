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
 * Main robot object.
 * Used by robot internals.
 * Inherits from timed robot for consistent frequency.
 */
object Robot: TimedRobot() {
	// Sensors
	val gyro = ADXRS450_Gyro()
	// Note: use the average value from the pressure sensor
	private val pressureSensor = AnalogInput(MiscConsts.AIN_PRESSURE)

	private val cameraServer = CameraServer.getInstance()
	val driverStation = DriverStation.getInstance()
	private val subsystemList = listOf(DriveSubsystem, IntakeSubsystem, ElevatorSubsystem, ClimberSubsystem)

	override fun robotInit() {
		// Put all of the subsystems on SmartDashboard
		subsystemList.forEach{ SmartDashboard.putData(it) }

		// Miscellaneous setups
		gyro.reset()
		// 2 ^ 12 average bits
		pressureSensor.averageBits = 12

		cameraServer.startAutomaticCapture()
	}

	// DISABLED //
	override fun disabledInit() {
		// Empty out the scheduler on disable
		Scheduler.getInstance().removeAll()
	}

	override fun disabledPeriodic() { }

	// ENABLED //
	fun initialize() {

	}

	override fun robotPeriodic() {
		Scheduler.getInstance().run()
	}

	// AUTONOMOUS //
	override fun autonomousInit() {
		// Reset all subsystems for autonomous
		subsystemList.forEach{ it.autoReset() }

		AutoMain.fetchData()
	}

	override fun autonomousPeriodic() {
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
