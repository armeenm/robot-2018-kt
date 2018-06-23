package frc.team4096.robot.util

import edu.wpi.first.wpilibj.CircularBuffer
import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.InstantCommand
import kotlin.math.abs

/*
"Commandify" any given lambda function, usually for subsystem calls.
Uses an inline anonymous function with a lambda as an argument (lambda has no parameters and returns Unit).
Creates an InstantCommand() object and overrides the execute function with the given lambda.
An InstantCommand() is a command with isFinished set to true.
The lambda is crossinlined to prevent non-local returns.
 */
inline fun Commandify(crossinline method: () -> Unit): Command = object: InstantCommand() {
	override fun execute() = method()
}

// Applies a deadband to a given value
fun applyDeadband(inputValue: Double, deadBand: Double) = if (abs(inputValue) >= deadBand) inputValue else 0.0

// Checks if checkVal is within deadBand to cmpVal
fun onTarget(checkVal: Double, cmpVal: Double, deadBand: Double) =
	abs(abs(cmpVal) - abs(checkVal)) < deadBand

enum class ControlState {
	OPEN_LOOP,
	CURRENT_CONTROL,
	VELOCITY_CONTROL,
	POSITION_CONTROL,
	PATH_FOLLOWING
}
