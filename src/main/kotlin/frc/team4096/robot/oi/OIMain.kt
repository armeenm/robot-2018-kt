@file:Suppress("unused")

package frc.team4096.robot.oi

import frc.team4096.engine.motion.DriveSignal
import frc.team4096.engine.motion.PFPath
import frc.team4096.engine.oi.XboxConsts
import frc.team4096.engine.oi.ZedXboxController
import frc.team4096.engine.util.commandify
import frc.team4096.robot.autonomous.modes.DriveForward
import frc.team4096.robot.climber.ClimberConsts
import frc.team4096.robot.climber.ClimberSubsystem
import frc.team4096.robot.climber.ManualClimberCmd
import frc.team4096.robot.drivetrain.DriveConsts
import frc.team4096.robot.drivetrain.DriveSubsystem
import frc.team4096.robot.drivetrain.GearState
import frc.team4096.robot.drivetrain.commands.DriveCmd
import frc.team4096.robot.drivetrain.commands.DriveDistanceCmd
import frc.team4096.robot.drivetrain.commands.FollowPathPFCmd
import frc.team4096.robot.drivetrain.commands.FollowPathRamseteCmd
import frc.team4096.robot.elevator.ElevatorConsts
import frc.team4096.robot.elevator.ElevatorSubsystem
import frc.team4096.robot.elevator.commands.AutoElevatorCmd
import frc.team4096.robot.elevator.commands.ManualElevatorCmd
import frc.team4096.robot.intake.IntakeConsts
import frc.team4096.robot.intake.IntakeSubsystem
import frc.team4096.robot.intake.commands.ManualIntakeCmd
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint

/**
 * Operator input object.
 * Handles all joystick activity.
 */
object OIMain {
    // Setup controllers with custom Ctrl-Z XboxController wrapper
    val XboxController1 = ZedXboxController(0)
    val XboxController2 = ZedXboxController(1)

    init {
        // Controller 1 (Main Driver)
        val waypoints = arrayOf(
                Waypoint(0.0, 0.0, 0.0),
                Waypoint(10.0, 0.0, 0.0)
        )
        val trajConf = Trajectory.Config(
                Trajectory.FitMethod.HERMITE_CUBIC,
                Trajectory.Config.SAMPLES_HIGH,
                0.02,
                9.0,
                4.0,
                60.0
        )
        val metadata = PFPath.Metadata(waypoints, trajConf, DriveConsts.DT_TRACK_WIDTH)
        val path = PFPath(metadata, "")
        path.generate()
        XboxController1.xButton.whenPressed(FollowPathRamseteCmd(path))

        // Drivetrain
        // Movement
        DriveSubsystem.defaultCommand = DriveCmd {
            DriveSignal(
                    XboxController1.getAxis(XboxConsts.Axis.LEFT_Y),
                    -XboxController1.getAxis(XboxConsts.Axis.RIGHT_X),
                    XboxController1.lbButton.get()
            )
        }
        // Shifting
        XboxController1.rbButton.apply {
            whenPressed(commandify { DriveSubsystem.gear = GearState.LOW })
            whenReleased(commandify { DriveSubsystem.gear = GearState.HIGH })
        }

        // Climber
        XboxController1.upDPad.whileHeld(ManualClimberCmd(ClimberConsts.MAX_FORWARD_SPEED))

        // Controller 2 (Secondary Driver)
        // Manual Elevator
        ElevatorSubsystem.defaultCommand = ManualElevatorCmd { XboxController2.getAxis(XboxConsts.Axis.LEFT_Y) }

        // Automatic Elevator
        XboxController2.downDPad.whenPressed(
                AutoElevatorCmd(ElevatorConsts.Positions.BOTTOM.pos)
        )
        XboxController2.rightDPad.whenPressed(
                AutoElevatorCmd(ElevatorConsts.Positions.NO_DRAG.pos)
        )
        XboxController2.leftDPad.whenPressed(
                AutoElevatorCmd(ElevatorConsts.Positions.SWITCH.pos)
        )
        XboxController2.upDPad.whenPressed(
                AutoElevatorCmd(ElevatorConsts.Positions.SCALE.pos)
        )

        // Intake
        // Wheels
        IntakeSubsystem.defaultCommand = ManualIntakeCmd {
            XboxController2.getAxis(XboxConsts.Axis.LT) * IntakeConsts.MAX_IN_SPEED -
                    XboxController2.getAxis(XboxConsts.Axis.RT) * IntakeConsts.MAX_OUT_SPEED
        }
        // Rotation
        XboxController2.yButton.apply {
            whenPressed(commandify {
                IntakeSubsystem.rotateHolding = false
                IntakeSubsystem.rotateSpeed = IntakeConsts.DEFAULT_ROTATE_SPEED
            })
            XboxController2.yButton.whenReleased(commandify { IntakeSubsystem.rotateHolding = true })
        }

        XboxController2.aButton.whenPressed(commandify { IntakeSubsystem.rotateHolding = false })

        // Intake
        XboxController2.bButton.apply {
            whenPressed(commandify { IntakeSubsystem.squeeze = IntakeSubsystem.SqueezeState.OUT })
            whenReleased(commandify { IntakeSubsystem.squeeze = IntakeSubsystem.SqueezeState.IN })
        }

        // Climber
        XboxController2.selectButton.whenPressed(commandify { ClimberSubsystem.release() })
    }
}
