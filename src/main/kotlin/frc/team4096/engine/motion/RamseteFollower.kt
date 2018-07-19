package frc.team4096.engine.motion

import frc.team4096.engine.math.cos
import frc.team4096.engine.math.sin
import jaci.pathfinder.Trajectory
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/*
vd = eq. 5.2
thetad =
 */

class RamseteFollower(private val trajectory: Trajectory) {
	private var currentSegmentIndex = 0
	var currentSegment: Trajectory.Segment = trajectory.segments[0]
		private set

	val isFinished
		get() = currentSegmentIndex == trajectory.segments.size - 1

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
			(pathV cos thetaError) + (gain(pathV, pathW) * ((xError cos theta) + (yError sin theta)))
		}

		fun calculateAngularVel(
			xError: Double,
			yError: Double,
			thetaError: Double,
			pathV: Double,
			pathW: Double,
			theta: Double
		) {
			pathW + b * pathV * (sin(thetaError) / thetaError) * ((xError cos theta) - (yError sin theta)) +
				gain(pathV, pathW) * thetaError
		}

		// k1 Gain Function
		private fun gain(v: Double, w: Double): Double {
			// Equation 5.9
			return 2 * zeta * sqrt(w.pow(2) + b * v.pow(2))
		}
	}
}