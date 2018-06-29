package frc.team4096.engine.util

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.CommandGroup
import edu.wpi.first.wpilibj.command.InstantCommand
import kotlin.math.abs

// Quickly make command groups, helpful for use in other command groups
fun commandGroup(create: CommandGroup.() -> Unit): CommandGroup {
	val group = CommandGroup()
	create.invoke(group)
	return group
}

fun onTarget(checkVal: Double, cmpVal: Double, deadBand: Double) =
	abs(abs(cmpVal) - abs(checkVal)) < deadBand

// Applies a deadband to a given value
fun applyDeadband(inputValue: Double, deadBand: Double) = if (abs(inputValue) >= deadBand) inputValue else 0.0

/*
"commandify" any given lambda function, usually for subsystem calls.
Uses an inline anonymous function with a lambda as an argument (lambda has no parameters and returns Unit).
Creates an InstantCommand() object and overrides the execute function with the given lambda.
An InstantCommand() is a command with isFinished set to true.
The lambda is crossinlined to prevent non-local returns.
 */
inline fun commandify(crossinline method: () -> Unit): Command = object: InstantCommand() {
	override fun execute() = method()
}