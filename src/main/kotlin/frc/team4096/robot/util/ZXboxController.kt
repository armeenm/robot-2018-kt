package frc.team4096.robot.util

import edu.wpi.first.wpilibj.GenericHID
import edu.wpi.first.wpilibj.XboxController
import edu.wpi.first.wpilibj.buttons.JoystickButton

class ZXboxController(port: Int): XboxController(port) {
	// Buttons
	val aButton = JoystickButton(this, XboxConsts.Button.A.id)
	val bButton = JoystickButton(this, XboxConsts.Button.B.id)
	val xButton = JoystickButton(this, XboxConsts.Button.X.id)
	val yButton = JoystickButton(this, XboxConsts.Button.Y.id)
	val lbButton = JoystickButton(this, XboxConsts.Button.LB.id)
	val rbButton = JoystickButton(this, XboxConsts.Button.RB.id)
	val startButton = JoystickButton(this, XboxConsts.Button.START.id)
	val selectButton = JoystickButton(this, XboxConsts.Button.SELECT.id)
	val leftStickButton = JoystickButton(this, XboxConsts.Button.LEFT_STICK.id)
	val rightStickButton = JoystickButton(this, XboxConsts.Button.RIGHT_STICK.id)

	// D-Pad Buttons
	val upDPad = JoystickPOV(this, XboxConsts.DPad.UP)
	val upRightDPad = JoystickPOV(this, XboxConsts.DPad.UP_RIGHT)
	val rightDPad = JoystickPOV(this, XboxConsts.DPad.RIGHT)
	val downRightDPad = JoystickPOV(this, XboxConsts.DPad.DOWN_RIGHT)
	val downDPad = JoystickPOV(this, XboxConsts.DPad.DOWN)
	val downLeftDPad = JoystickPOV(this, XboxConsts.DPad.DOWN_LEFT)
	val leftDPad = JoystickPOV(this, XboxConsts.DPad.LEFT)
	val upLeftDPad = JoystickPOV(this, XboxConsts.DPad.UP_LEFT)

	fun getAxis(axis: XboxConsts.Axis, inverted: Boolean = false) =
		applyDeadband(this.getRawAxis(axis.id) * if (inverted) -1 else 1, XboxConsts.DEAD_BAND)
}

class JoystickPOV(private val joystick: GenericHID, private val dPadButton: XboxConsts.DPad):
		JoystickButton(joystick, dPadButton.id) {
	override fun get(): Boolean = joystick.pov == dPadButton.id
}
