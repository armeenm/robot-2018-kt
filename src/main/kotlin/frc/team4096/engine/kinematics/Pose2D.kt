package frc.team4096.engine.kinematics

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D

class Pose2D(x: Double = 0.0, y: Double = 0.0, private val yaw: Double = 0.0) {
    val translation = Vector2D(x, y)
    val rotation = Rotation2D()
}