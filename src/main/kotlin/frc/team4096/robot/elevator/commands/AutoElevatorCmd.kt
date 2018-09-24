package frc.team4096.robot.elevator.commands

import com.ctre.phoenix.motorcontrol.ControlMode
import edu.wpi.first.wpilibj.command.Command
import frc.team4096.engine.util.onTarget
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.elevator.ElevatorSubsystem

/**
 * Move elevator a given distance.
 * Uses Talon SRX Motion Magic (trapezoidal motion profiling).
 *
 * @param distance Distance to travel
 */
class AutoElevatorCmd(distance: Double) : Command() {
    private val talonDistance = -distance * ElevatorConsts.ENC_TICKS_PER_FOOT
    init {
        this.requires(ElevatorSubsystem)
        // TODO: Maybe this can be interruptible??
        // Idea: set interruptible _only_ if joystick values are outside deadband.
        this.isInterruptible = false
    }

    override fun initialize() {
        println(talonDistance)
        ElevatorSubsystem.hwState = ElevatorSubsystem.ElevatorState.FREE
    }

    override fun execute() {
        ElevatorSubsystem.masterMotor.set(ControlMode.MotionMagic, talonDistance)
        ElevatorSubsystem.slaveMotor.follow(ElevatorSubsystem.masterMotor)
    }

    override fun isFinished() =
            onTarget(
                    ElevatorSubsystem.masterMotor.getSelectedSensorPosition(0).toDouble(),
                    talonDistance,
                    5000.0
            )

    override fun end() {
        println("MM Done!")
        ElevatorSubsystem.stop()
        ElevatorSubsystem.hwState = ElevatorSubsystem.ElevatorState.HOLDING
    }
}