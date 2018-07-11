package frc.team4096.engine.motion

import frc.team4096.engine.math.cos
import jaci.pathfinder.Trajectory

/*
vd = eq. 5.2
thetad =
 */

class RamseteFollower(val traj: Trajectory) {

	companion object {
		// Constants
		private const val b = 0.5
		private const val zeta = 0.175

		fun calculateLinearVel(
			xError: Double,
			yError: Double,
			thetaError: Double,
			pathV: Double,
			pathW: Double,
			theta: Double
		) {
			(pathV cos thetaError)
		}

		fun gain(v: Double, w: Double) {

		}
	}
}