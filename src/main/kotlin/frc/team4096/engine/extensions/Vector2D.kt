package frc.team4096.engine.extensions

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D

operator fun Vector2D.plus(other: Vector2D) =
	Vector2D(this.x + other.x, this.y + other.y)

operator fun Vector2D.minus(other: Vector2D) =
	Vector2D(this.x - other.x, this.y - other.y)

operator fun Vector2D.unaryMinus() = Vector2D(-this.x, -this.y)
