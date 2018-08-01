package frc.team4096.engine.kinematics

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D

class Pose2D(x: Double = 0.0, y: Double, private val yaw: Double) {
	val translation = Vector2D(x, y)
	val rotation = Rotation2D()
}
