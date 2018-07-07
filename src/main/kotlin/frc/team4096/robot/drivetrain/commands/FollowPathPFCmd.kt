package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.motion.util.PIDVAVals
import frc.team4096.engine.sensors.ADXRS450
import frc.team4096.robot.drivetrain.DriveConsts
import frc.team4096.robot.drivetrain.DriveSubsystem
import jaci.pathfinder.Pathfinder
import jaci.pathfinder.followers.EncoderFollower

/**
 * Follows path using Jaci's EncoderFollower.
 */
class FollowPathPFCmd(path: PFPath, pidvaVals: PIDVAVals) : Command() {
	private val leftFollower = EncoderFollower(path.modifier!!.leftTrajectory)
	private val rightFollower = EncoderFollower(path.modifier!!.rightTrajectory)

	init {
		isInterruptible = false
		requires(DriveSubsystem)

		listOf(leftFollower, rightFollower).forEach {
			it.configurePIDVA(pidvaVals.kP, pidvaVals.kI, pidvaVals.kD, pidvaVals.kV, pidvaVals.kA)
		}

		leftFollower.configureEncoder(
			DriveSubsystem.leftEncoder.get(),
			DriveConsts.ENC_TICKS_PER_REV,
			DriveConsts.WHEEL_DIAMETER
		)
		rightFollower.configureEncoder(
			DriveSubsystem.rightEncoder.get(),
			DriveConsts.ENC_TICKS_PER_REV,
			DriveConsts.WHEEL_DIAMETER
		)
	}

	override fun initialize() {
		DriveSubsystem.apply { leftEncoder.reset(); rightEncoder.reset() }
		leftFollower.reset()
		rightFollower.reset()
	}

	override fun execute() {
		// Distance
		val l = leftFollower.calculate(DriveSubsystem.leftEncoder.get())
		val r = rightFollower.calculate(DriveSubsystem.rightEncoder.get())
		// Heading
		val gyroHeading = ADXRS450.angle
		val desiredHeading = Pathfinder.r2d(leftFollower.heading)
		val angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading)
		val turn = 0.8 * (-1.0 / 80.0) * angleDifference

		DriveSubsystem.diffDrive.tankDrive(l + turn, r - turn)
	}

	override fun isFinished() = leftFollower.isFinished && rightFollower.isFinished
}