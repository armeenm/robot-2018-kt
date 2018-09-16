package frc.team4096.engine.kinematics

import frc.team4096.engine.math.epsilonEquals

fun Twist2D.tankInverseKinematics(trackWidth: Double): Pair<Double, Double> {
    if (this.dtheta epsilonEquals 0.0) {
        return this.dx to this.dx
    }

    val deltaV = trackWidth * this.dtheta / 2
    return this.dx - deltaV to this.dx + deltaV
}
