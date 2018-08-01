package frc.team4096.engine.kinematics

import frc.team4096.engine.math.atan2
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import org.apache.commons.math3.util.Precision.EPSILON

/**
 * Taken from 2898 math libraries.
 */
class Rotation2D {
	companion object { // Allows us to have static traits
		fun createFromRadians(angleRadians: Double): Rotation2D =
			Rotation2D(Math.cos(angleRadians), Math.sin(angleRadians))

		fun createFromDegrees(angleDegrees: Double): Rotation2D =
			createFromRadians(Math.toRadians(angleDegrees))
	}


	// represents as [cos(θ), sin(θ)]. default is 0° (pointing right)
	// for instance, 45° -> [cos(45°), sin(45°)] -> [(√2)/2, (√2)/2]
	var rotation: Vector2D = Vector2D(1.0, 0.0)

	constructor()

	constructor(cos: Double, sin: Double) {
		rotation = Vector2D(cos, sin).normalize()
	}

	constructor(toSet: Rotation2D) {
		rotation = toSet.rotation.normalize()
	}

	constructor(toSetVector: Vector2D) {
		rotation = toSetVector.normalize()
	}

	var cos: Double
		get() = rotation.x
		set(value) {
			rotation = Vector2D(value, rotation.x)
		}
	var sin: Double
		get() = rotation.y
		set(value) {
			rotation = Vector2D(rotation.y, value)
		}

	val tan: Double
		get() =
			if (rotation.x > EPSILON) rotation.y / rotation.x
			else if (rotation.y >= 0.0) Double.POSITIVE_INFINITY
			else Double.NEGATIVE_INFINITY

	var radians: Double
		get() = rotation.atan2
		set(value) {
			rotation = createFromRadians(value).rotation
		}

	var degrees: Double
		get() = Math.toDegrees(radians)
		set(value) {
			rotation = createFromDegrees(value).rotation
		}

	var theta: Double
		get() = radians
		set(value) {
			radians = value
		}

	/*
	* Rotation matrix operation to add two Rotation2Ds
	*/
	/*
	infix fun rotateBy(toRotateBy: Rotation2D): Rotation2D {
		val rotated = rotateVector2D(rotation, toRotateBy.rotation)
		return Rotation2D(rotated)
	}
	*/

	// Returns inverse of rotation, can undo the effects of the above matrix rotation
	val inverse: Rotation2D
		get() = Rotation2D(cos, -sin)

	/*
	fun interpolate(upperVal: Rotation2D, interpolatePoint: Double): Rotation2D {
		when {
			interpolatePoint <= 0 -> return Rotation2D(this)
			interpolatePoint >= 1 -> return Rotation2D(upperVal)
			else -> return this.rotateBy(
				Rotation2D.createFromRadians(inverse.rotateBy(upperVal).radians * interpolatePoint)
			)
		}
	}
	*/

	override fun toString(): String {
		return "cos: ${"%.3f".format(cos)} sin: ${"%.3f".format(sin)} deg: ${"%.3f".format(degrees)}"
	}
}

