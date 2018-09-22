package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.robot.drivetrain.DriveConsts
import frc.team4096.robot.drivetrain.DriveSubsystem
import frc.team4096.robot.sensors.Gyro
import jaci.pathfinder.Pathfinder
import jaci.pathfinder.followers.EncoderFollower

/**
 * Follows path using Jaci's EncoderFollower.
 *
 * @param path Path object to follow
 * @param pidvaVals PIDVA gains to configure follower with. I is ignored and A is ADDITIONAL gain.
 */
class FollowPathPFCmd(path: PFPath, pidvaVals: PIDVAVals = DriveConsts.PIDVA_GAINS_PF) : Command() {
    private val leftFollower = EncoderFollower(path.modifier!!.leftTrajectory)
    private val rightFollower = EncoderFollower(path.modifier!!.rightTrajectory)
    private val followers = listOf(leftFollower, rightFollower)

    private var l = 0.0
    private var r = 0.0
    private var gyroHeading = 0.0
    private var desiredHeading = 0.0
    private var angleDifference = 0.0
    private var turn = 0.0

    init {
        isInterruptible = false
        requires(DriveSubsystem)

        followers.forEach {
            it.configurePIDVA(pidvaVals.kP, pidvaVals.kI, pidvaVals.kD, pidvaVals.kV, pidvaVals.kA)
        }

        leftFollower.configureEncoder(
                DriveSubsystem.leftEncoder.get(),
                DriveConsts.ENCODER_TPR,
                DriveConsts.DT_WHEEL_DIAMETER
        )

        rightFollower.configureEncoder(
                DriveSubsystem.rightEncoder.get(),
                DriveConsts.ENCODER_TPR,
                DriveConsts.DT_WHEEL_DIAMETER
        )
    }

    override fun initialize() {
        DriveSubsystem.apply { leftEncoder.reset(); rightEncoder.reset() }
        Gyro.reset()
        followers.forEach(EncoderFollower::reset)
    }

    override fun execute() {
        // Distance
        l = leftFollower.calculate(DriveSubsystem.leftEncoder.get())
        r = rightFollower.calculate(DriveSubsystem.rightEncoder.get())
        // Heading
        gyroHeading = -Gyro.angle
        desiredHeading = Pathfinder.r2d(leftFollower.heading)
        angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading)
        turn = 5 * (-1.0 / 80.0) * angleDifference

        l += turn
        r -= turn

        DriveSubsystem.diffDrive.tankDrive(-l, -r)
    }

    override fun isFinished() = leftFollower.isFinished && rightFollower.isFinished

    override fun end() = DriveSubsystem.diffDrive.tankDrive(0.0, 0.0)
}