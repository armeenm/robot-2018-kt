package frc.team4096.robot.elevator

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.util.onTarget

/**
 * Move elevator a given distance.
 * Uses Talon SRX Motion Magic (trapezoidal motion profiling).
 *
 * @param distance Distance to travel
 */
class AutoElevatorCmd(private val distance: Double) : Command() {
	init {
		this.requires(ElevatorSubsystem)
		// TODO: Maybe this can be interruptible??
		// Idea: set interruptible _only_ if joystick values are outside deadband.
		this.isInterruptible = false
	}

	override fun initialize() {
		ElevatorSubsystem.hwState = ElevatorSubsystem.ElevatorState.FREE
	}

	override fun execute() =
		ElevatorSubsystem.masterMotor.set(ControlMode.MotionMagic, distance)

	override fun isFinished() =
		onTarget(
			ElevatorSubsystem.masterMotor.sensorCollection.quadraturePosition.toDouble(),
			distance,
			5000.0
		)

	override fun end() {
		ElevatorSubsystem.hwState = ElevatorSubsystem.ElevatorState.HOLDING
	}
}