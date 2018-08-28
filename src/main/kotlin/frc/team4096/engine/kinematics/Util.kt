package frc.team4096.engine.kinematics

import frc.team4096.engine.math.epsilonEquals

fun inverseKinematics(velocity: Twist2D, wheelbaseWidth: Double): Pair<Double, Double> {
    if (velocity.dtheta epsilonEquals 0.0) {
        return velocity.dx to velocity.dx
    }

    val deltaV = wheelbaseWidth * velocity.dtheta / 2
    return velocity.dx - deltaV to velocity.dx + deltaV
}
