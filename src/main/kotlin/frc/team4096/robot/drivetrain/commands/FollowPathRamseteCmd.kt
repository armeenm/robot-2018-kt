package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.kinematics.Pose2D
import frc.team4096.engine.kinematics.tankInverseKinematics
import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.engine.motion.RamseteFollower
import frc.team4096.engine.motion.classicControl.PIDFController
import frc.team4096.robot.drivetrain.DriveConsts
import frc.team4096.robot.sensors.Gyro
import frc.team4096.robot.drivetrain.DriveSubsystem

class FollowPathRamseteCmd(
        val path: PFPath,
        pidvaVals: PIDVAVals = DriveConsts.PIDVA_GAINS_PF,
        kBeta: Double = DriveConsts.BETA,
        kZeta: Double = DriveConsts.ZETA,
        val trackWidth: Double = DriveConsts.DT_TRACK_WIDTH,
        val setPoseToPath: Boolean = false
) : Command() {
    private val ramseteFollower = RamseteFollower(path.trajectory!!, kBeta, kZeta)

    private val pidLeft = PIDFController(pidvaVals)
    private val pidRight = PIDFController(pidvaVals)
    private var leftOut = 0.0
    private var rightOut = 0.0

    init {
        requires(DriveSubsystem)
        isInterruptible = false
    }

    override fun initialize() {
        if (setPoseToPath) {
            DriveSubsystem.apply {
                leftEncoder.reset()
                rightEncoder.reset()
            }
            Gyro.reset()
            val segment = path.trajectory!!.segments[0]
            DriveSubsystem.pose = Pose2D(segment.x, segment.y, segment.heading)
        }
        ramseteFollower.reset()
    }

    override fun execute() {
        val twist = ramseteFollower.update(DriveSubsystem.pose)
        val (left, right) = twist.tankInverseKinematics(trackWidth)
        /*
        pidLeft.apply {
            setpoint = left
            leftOut = calculate(DriveSubsystem.leftEncoder.rate)
        }
        pidRight.apply {
            setpoint = right
            rightOut = calculate(DriveSubsystem.rightEncoder.rate)
        }
        */
        DriveSubsystem.diffDrive.tankDrive(
                -left / DriveConsts.DT_MAX_VEL,
                -right / DriveConsts.DT_MAX_VEL
        )
    }

    override fun isFinished() = ramseteFollower.isFinished

    override fun end() {
        println("Ramsyeet Finished!")
    }
}