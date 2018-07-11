package frc.team4096.robot

import edu.wpi.first.wpilibj.TimedRobot
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard
import frc.team4096.engine.sensors.ADXRS450
import frc.team4096.robot.autonomous.AutoMain
import frc.team4096.robot.climber.ClimberSubsystem
import frc.team4096.robot.drivetrain.DriveSubsystem
import frc.team4096.robot.elevator.ElevatorSubsystem
import frc.team4096.robot.intake.IntakeSubsystem
import frc.team4096.robot.misc.cameraServer
import frc.team4096.robot.misc.scheduler
import frc.team4096.robot.sensors.PressureSensor
import kotlinx.coroutines.experimental.launch

/**
 * Main robot class, instantiated by WPILib.
 * Inherits from timed robot for consistent frequency.
 */
class Robot : TimedRobot() {
	companion object {
		val subsystemList = listOf(DriveSubsystem, IntakeSubsystem, ElevatorSubsystem, ClimberSubsystem)
	}

	init {
		ADXRS450
		PressureSensor
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

	override fun robotPeriodic() {
		scheduler.run()
	}

	// DISABLED //
	override fun disabledInit() {
		scheduler.removeAll()
	}

	override fun disabledPeriodic() {}

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
	fun log() {
		subsystemList.forEach {
			when {
				isAutonomous -> it.autoLog()
				isOperatorControl -> it.teleopLog()
				else -> it.log()
			}
		}
	}
}
