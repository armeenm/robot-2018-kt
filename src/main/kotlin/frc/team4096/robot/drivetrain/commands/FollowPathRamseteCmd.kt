package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.kinematics.inverseKinematics
import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.engine.motion.RamseteFollower
import frc.team4096.robot.drivetrain.DriveConsts
import frc.team4096.robot.drivetrain.DriveSubsystem

class FollowPathRamseteCmd(
        path: PFPath,
        pidvaVals: PIDVAVals = DriveConsts.PIDVA_GAINS_PF,
        kBeta: Double = DriveConsts.BETA,
        kZeta: Double = DriveConsts.ZETA,
        val trackWidth: Double = DriveConsts.DT_TRACK_WIDTH
) : Command() {
    private val ramseteFollower = RamseteFollower(path.trajectory!!, kBeta, kZeta)

    /*
    private val velLambda = {
        inverseKinematics(ramseteFollower.update(DriveSubsystem.pose), trackWidth)
    }
    private val pidLeft = AsyncPIDFController(
            pidvaVals,
            { velLambda().first },
            { }
    )
    */

    override fun initialize() {
        DriveSubsystem.apply { leftEncoder.reset(); rightEncoder.reset() }
    }

    override fun execute() {
        val driveVals = inverseKinematics(ramseteFollower.update(DriveSubsystem.pose), trackWidth)
        DriveSubsystem.diffDrive.tankDrive(
                driveVals.first / DriveConsts.DT_MAX_VEL,
                driveVals.second / DriveConsts.DT_MAX_VEL
        )
    }

    override fun isFinished() = ramseteFollower.isFinished
}