package frc.team4096.engine.util

import edu.wpi.first.wpilibj.command.Command
import edu.wpi.first.wpilibj.command.CommandGroup
import edu.wpi.first.wpilibj.command.InstantCommand
import kotlin.math.abs

/**
 * Easily make command groups on the spot.
 * Helpful for addParallels in other command groups.
 *
 * @param create Lambda with desired CommandGroup actions (e.g. addSequential(cmd))
 */
fun commandGroup(create: CommandGroup.() -> Unit): CommandGroup {
    val group = CommandGroup()
    create.invoke(group)
    return group
}

/**
 * Check if a value is within a certain deadband to a target.
 *
 * @param cmpVal Value to check
 * @param checkVal Value to check against
 * @param deadBand Deadband to be within
 */
fun onTarget(checkVal: Double, cmpVal: Double, deadBand: Double) =
        abs(abs(cmpVal) - abs(checkVal)) < deadBand

// Applies a deadband to a given value
fun applyDeadband(inputValue: Double, deadBand: Double) = if (abs(inputValue) >= deadBand) inputValue else 0.0

/**
 * Transform a given lambda into an InstantCommand.
 * Useful for oneshot activities like an OIMain button.
 * Inlined and crossinlined for performance and non-local return prevention, respectively.
 *
 * @param method Lambda to run as command
 */
inline fun commandify(crossinline method: () -> Unit): Command = object : InstantCommand() {
    override fun execute() = method()
}