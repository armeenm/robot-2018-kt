package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.motion.PIDVAVals
import frc.team4096.engine.motion.classicControl.PIDVAController
import frc.team4096.engine.motion.profiles.TrapezoidalMP
import frc.team4096.robot.drivetrain.DriveSubsystem

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
            { DriveSubsystem.rightMotorGroup.set(-it) }
    )

    private val leftController = PIDVAController(pidvaVals, leftProfile)
    private val rightController = PIDVAController(pidvaVals, rightProfile)
    private val controllers = listOf(leftController, rightController)

    init {
        this.requires(DriveSubsystem)
        this.isInterruptible = false
    }

    override fun initialize() {
        DriveSubsystem.rightEncoder.reset()
        DriveSubsystem.leftEncoder.reset()
        controllers.forEach(PIDVAController::enable)
    }

    override fun isFinished() = leftController.profile.isFinished && rightController.profile.isFinished

    override fun end() {
        println("DriveDistance complete!")
        DriveSubsystem.stop()
        controllers.forEach(PIDVAController::disable)
    }
}
