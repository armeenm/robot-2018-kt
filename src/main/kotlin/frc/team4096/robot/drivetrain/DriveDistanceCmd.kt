package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.motion.PIDVAController
import frc.team4096.engine.motion.TrapezoidalMP
import frc.team4096.engine.motion.util.PIDVAVals

/**
 * Drive a given distance with trapezoidal motion profiles.
 *
 * @param distance Distance to drive
 * @param maxAccel Maximum acceleration
 * @param maxVel Maximum velocity
 * @param pidvaVals PIDVA gain constants
 */
class DriveDistanceCmd(
	private val distance: Double,
	private val maxAccel: Double,
	private val maxVel: Double,
	pidvaVals: PIDVAVals
) : Command() {
	private val leftProfile = TrapezoidalMP(
		distance, maxVel, maxAccel,
		{ DriveSubsystem.leftEncoder.distance },
		{ DriveSubsystem.leftMotorGroup.set(it) }
	)
	private val rightProfile = TrapezoidalMP(
		distance, maxVel, maxAccel,
		{ DriveSubsystem.rightEncoder.distance },
		{ DriveSubsystem.rightMotorGroup.set(it) }
	)

	private var leftController = PIDVAController(pidvaVals, leftProfile)
	private var rightController = PIDVAController(pidvaVals, rightProfile)

	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = false
	}

	override fun initialize() {
		leftController.enable()
		rightController.enable()
	}

	override fun isFinished() =
		leftController.profile.isFinished && rightController.profile.isFinished
}
