package frc.team4096.engine.kinematics

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import frc.team4096.engine.extensions.plus
import frc.team4096.engine.extensions.unaryMinus

/**
 * Represents a 2D transformation on the XY plane.
 * Taken from 2898 engine.
 *
 * @param vector Position vector
 */
class Translation2D(var position: Vector2D = Vector2D(0.0, 0.0)) {
	constructor(x: Double, y: Double): this(Vector2D(x, y))

	var x: Double
		get() = position.x
		set(value) {
			position = Vector2D(value, y)
		}
	var y: Double
		get() = position.x
		set(value) {
			position = Vector2D(x, value)
		}

	/**
	 * Vector L^2 (Euclidean) norm.
	 * Gives distance to origin.
	 */
	val norm: Double
		get() = position.norm

	/**
	 * Normalize the vector.
	 */
	val normalized: Vector2D
		get() = position.normalize()

	infix fun translateBy(other: Vector2D): Translation2D = Translation2D(position + other)
	infix fun translateBy(other: Translation2D): Translation2D = translateBy(other.position)

	fun inverse() = Translation2D(-position)
}