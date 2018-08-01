package frc.team4096.engine.oi.util

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.buttons.JoystickButton
import frc.team4096.engine.oi.XboxConsts

/**
 * Wrapper around D-Pad to make it usable as a Button class.
 *
 * @param joystick Joystick (e.g. Xbox controller)
 * @param dPadButton Chosen angle
 * @constructor Constructor
 */
class JoystickPOV(private val joystick: GenericHID, private val dPadButton: XboxConsts.DPad) :
	JoystickButton(joystick, dPadButton.id) {
	// Get method should return whether the D-Pad axis is at the specified angle, effectively making a button.
	override fun get(): Boolean = joystick.pov == dPadButton.id
}
