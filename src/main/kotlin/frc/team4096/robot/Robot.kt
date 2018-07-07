package frc.team4096.robot

import edu.wpi.first.wpilibj.CameraServer
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.command.Scheduler
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.engine.sensors.ADXRS450
import frc.team4096.robot.autonomous.AutoMain
import frc.team4096.robot.climber.ClimberSubsystem
import frc.team4096.robot.drivetrain.DriveSubsystem
import frc.team4096.robot.elevator.ElevatorSubsystem
import frc.team4096.robot.intake.IntakeSubsystem
import frc.team4096.robot.sensors.PressureSensor
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * Main robot class, instantiated by WPILib.
 * Inherits from timed robot for consistent frequency.
 */
class Robot : TimedRobot() {
	companion object {
		// Software
		val cameraServer: CameraServer = CameraServer.getInstance()
		val driverStation: DriverStation = DriverStation.getInstance()
		val scheduler: Scheduler = Scheduler.getInstance()

		val sensorList = listOf(ADXRS450, PressureSensor)
		private val subsystemList = listOf(DriveSubsystem, IntakeSubsystem, ElevatorSubsystem, ClimberSubsystem)
	}

	override fun robotInit() {
		// Hardware
		ADXRS450.reset()

		// Software
		cameraServer.startAutomaticCapture()
		// SmartDashboard
		subsystemList.forEach { SmartDashboard.putData(it) }
		SmartDashboard.putData(ADXRS450)
		launch { log() }
	}

	// DISABLED //
	override fun disabledInit() {
		// Empty out the scheduler
		scheduler.removeAll()
	}

	override fun disabledPeriodic() {}

	// ENABLED //
	override fun robotPeriodic() {
		scheduler.run()
	}

	// AUTONOMOUS //
	override fun autonomousInit() {
		// Reset all subsystems for autonomous
		subsystemList.forEach { it.autoReset() }

		AutoMain.fetchData()
	}

	override fun autonomousPeriodic() {
		AutoMain.runAuto()
	}

	// TELE-OPERATED //
	override fun teleopInit() {
		// Clear out scheduler, potentially from autonomous
		scheduler.removeAll()
		// Reset all subsystems for teleop
		subsystemList.forEach { it.teleopReset() }
	}

	override fun teleopPeriodic() {}

	// TEST //
	override fun testInit() {}

	override fun testPeriodic() {}

	// MISC //
	/**
	 * General robot logging suspend function.
	 */
	suspend fun log() {
		while (true) {
			subsystemList.forEach {
				when {
					isAutonomous -> it.autoLog()
					isOperatorControl -> it.teleopLog()
					else -> it.log()
				}
			}

			SmartDashboard.putNumber("Pressure", PressureSensor.pressure)

			// Wait 100ms (10Hz)
			delay(100)
		}
	}
}
