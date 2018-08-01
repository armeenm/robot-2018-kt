package frc.team4096.robot.drivetrain

import edu.wpi.first.wpilibj.DoubleSolenoid

enum class GearState(val solenoidState: DoubleSolenoid.Value) {
	HIGH(DoubleSolenoid.Value.kForward),
	LOW(DoubleSolenoid.Value.kReverse),
	NEUTRAL(DoubleSolenoid.Value.kOff)
}