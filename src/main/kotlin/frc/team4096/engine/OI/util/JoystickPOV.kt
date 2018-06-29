package frc.team4096.engine.OI.util

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.buttons.JoystickButton
import frc.team4096.robot.util.XboxConsts

// Button class for D-Pad buttons since it's treated like a 360-degree axis.
class JoystickPOV(private val joystick: GenericHID, private val dPadButton: XboxConsts.DPad):
		JoystickButton(joystick, dPadButton.id) {
	// Get method should return whether the D-Pad axis is at the specified angle, effectively making a button.
	override fun get(): Boolean = joystick.pov == dPadButton.id
}
