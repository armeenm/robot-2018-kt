package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.kinematics.inverseKinematics
import frc.team4096.engine.motion.classicControl.AsyncPIDFController
import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.engine.motion.RamseteFollower
import frc.team4096.robot.drivetrain.DriveConsts
import frc.team4096.robot.drivetrain.DriveSubsystem

class FollowPathRamseteCmd(
        path: PFPath,
        pidvaVals: PIDVAVals = DriveConsts.kPIDVAGainsPF,
        kBeta: Double = DriveConsts.kBeta,
        kZeta: Double = DriveConsts.kZeta,
        trackWidth: Double = DriveConsts.kTrackWidth
) : Command() {
    private val ramseteFollower = RamseteFollower(path.trajectory!!, kBeta, kZeta)
    private val velLambda = {
        inverseKinematics(ramseteFollower.getRobotVel(DriveSubsystem.pose), trackWidth)
    }
    private val pidLeft = AsyncPIDFController(
            pidvaVals,
            { velLambda().first },
            { }
    )

    override fun initialize() {
        DriveSubsystem.apply { leftEncoder.reset(); rightEncoder.reset() }
    }

    override fun isFinished(): Boolean {
        return false
    }
}