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
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch

/**
 * Main robot class, instantiated by WPILib.
 * Inherits from timed robot for consistent frequency.
 */
class Robot: TimedRobot() {
	companion object {
		// Hardware
		val gyro = ADXRS450_Gyro()
		val pressureSensor = AnalogInput(MiscConsts.AIN_PRESSURE)

		// Software
		val cameraServer: CameraServer = CameraServer.getInstance()
		val driverStation: DriverStation = DriverStation.getInstance()
		val scheduler: Scheduler = Scheduler.getInstance()

		private val subsystemList = listOf(DriveSubsystem, IntakeSubsystem, ElevatorSubsystem, ClimberSubsystem)
	}

	override fun robotInit() {
		// Hardware
		gyro.reset()
		pressureSensor.averageBits = MiscConsts.PRESSURE_AVG_BITS

		// Software
		cameraServer.startAutomaticCapture()
		// SmartDashboard
		subsystemList.forEach { SmartDashboard.putData(it) }
		SmartDashboard.putData(gyro)
		launch { log() }
	}

	// DISABLED //
	override fun disabledInit() {
		// Empty out the scheduler
		scheduler.removeAll()
	}

	override fun disabledPeriodic() { }

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
		Scheduler.getInstance().removeAll()
		// Reset all subsystems for teleop
		subsystemList.forEach { it.teleopReset() }
	}

	override fun teleopPeriodic() { }

	// TEST //
	override fun testInit() { }

	override fun testPeriodic() { }

	// MISC //
	/**
	 * General robot logging suspend function.
	 */
	suspend fun log() {
		while (true) {
			SmartDashboard.putNumber("Pressure", analogToPressure(pressureSensor.averageVoltage))

			// Wait 100ms (10Hz)
			delay(100)
		}
	}

	/**
	 * Convert analog pressure sensor values (V) to pressure (PSI).
	 * Equation from REV Robotics part datasheet (REV-11-1107-DS-00).
	 *
	 * @param vOut Voltage output from pressure sensor
	 */
	private fun analogToPressure(vOut: Double) = 250 * vOut / 5.0 - 25
}
