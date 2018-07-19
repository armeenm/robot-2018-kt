package frc.team4096.engine.kinematics

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D

/**
 * Stores "twist" of 2D position (2D linear + 2D angular).
 * Taken from 2898 engine, modeled from 254 and ROS data types.
 *
 * @param dx Delta position, x component
 * @param dy Delta position, y component
 * @param dtheta Delta position, yaw component
 */
data class Twist2d(var dx: Double, var dy: Double, var dtheta: Double) {
	constructor(linear: Vector2D, angular: Vector2D) : this(linear.x, linear.y, angular.atan2)
	constructor (linear: Translation2d, angular: Rotation2d) : this(linear.x, linear.y, angular.radians)

	override fun toString(): String =
		"dx: ${"%.3f".format(dx)}, dy: ${"%.3f".format(dy)}, dtheta: ${"%.3f".format(dtheta)}"

}
