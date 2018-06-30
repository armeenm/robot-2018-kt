package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.command.Command

/**
 * Drive a given distance with trapezoidal motion profiles.
 *
 * @param distance Distance to drive
 * @param maxAccel Maximum acceleration
 * @param maxVel Maximum velocity
 */
class DriveDistanceCmd(val distance: Double, val maxAccel: Double, val maxVel: Double): Command() {
	init {
		this.requires(DriveSubsystem)
		this.isInterruptible = false
	}

	override fun execute() {
		super.execute()
	}

	override fun isFinished(): Boolean {
		TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
	}
}
