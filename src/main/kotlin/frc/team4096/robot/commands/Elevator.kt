package frc.team4096.robot.commands

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.command.Command
import frc.team4096.robot.subsystems.ElevatorSubsystem
import frc.team4096.robot.util.ElevatorConsts
import frc.team4096.engine.util.onTarget

/**
 * Manual elevator motion.
 * Intended for use with a joystick.
 * Does not finish but is interruptible.
 *
 * @param speed Speed to move elevator at
 */
class ManualElevatorCmd(private var speed: Double): Command() {
	init {
		this.requires(ElevatorSubsystem)
		this.isInterruptible = true
	}

	override fun execute() {
		ElevatorSubsystem.speed =
			speed * ElevatorConsts.MAX_OPEN_LOOP_SPEED
	}

	override fun isFinished() = false

	override fun end() { ElevatorSubsystem.speed = 0.0 }

	override fun interrupted() = this.end()
}

/**
 * Move elevator a given distance.
 * Uses Talon SRX Motion Magic (trapezoidal motion profiling).
 *
 * @param distance Distance to travel
 */
class AutoElevatorCmd(private val distance: Double): Command() {
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

	override fun interrupted() = this.end()
}