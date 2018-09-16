package frc.team4096.engine.motion

import frc.team4096.engine.kinematics.Pose2D
import frc.team4096.engine.kinematics.Twist2D
import frc.team4096.engine.math.boundRadians
import frc.team4096.engine.math.cos
import frc.team4096.engine.math.epsilonEquals
import frc.team4096.engine.math.sin
import jaci.pathfinder.Trajectory
import org.apache.commons.math3.util.Precision.EPSILON
import java.security.InvalidParameterException
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * RAMSETE non-linear time-varying reference tracker using Pathfinder (v1) trajectories.
 * https://www.dis.uniroma1.it/~labrob/pub/papers/Ramsete01.pdf
 *
 * @param trajectory Pathfinder trajectory
 * @param kBeta Beta constant
 * @param kZeta Zeta constant
 */
class RamseteFollower(val trajectory: Trajectory, private val kBeta: Double, private val kZeta: Double) {

    init {
        if (kZeta >= 1.0 || kZeta <= 0.0) {
            throw InvalidParameterException("kZeta must be between 0 and 1!")
        }

        if (kBeta < 0.0) {
            throw InvalidParameterException("kBeta must be greater than 0!")
        }
    }

    private var currentSegmentIndex = 0
    var currentSegment: Trajectory.Segment = trajectory.segments[0]
        private set

    val isFinished
        get() = currentSegmentIndex == trajectory.segments.size - 1

    /**
     * Returns desired linear and angular velocity of the robot.
     */
    fun update(pose: Pose2D): Twist2D {
        // Update the current segment
        if (currentSegmentIndex >= trajectory.segments.size) return Twist2D(0.0, 0.0, 0.0)

        currentSegment = trajectory.segments[currentSegmentIndex]

        // Calculate X and Y error
        val xError = currentSegment.x - pose.translation.x
        val yError = currentSegment.y - pose.translation.y

        // Calculate Theta Error
        var thetaError = (currentSegment.heading - pose.rotation.radians).boundRadians()
        thetaError = thetaError.let { if (it epsilonEquals 0.0) EPSILON else it }

        // Linear Velocity of the Segment
        val segVel = currentSegment.velocity

        // Angular Velocity of the Segment
        val segAngVel = if (currentSegmentIndex == trajectory.segments.size - 1) {
            0.0
        } else {
            (trajectory.segments[currentSegmentIndex + 1].heading - currentSegment.heading).boundRadians() /
                    currentSegment.dt
        }

        // Calculate Linear and Angular Velocity based on errors
        val v = calculateLinearVel(xError, yError, thetaError, segVel, segAngVel, pose.rotation.radians)
        val w = calculateAngularVel(xError, yError, thetaError, segVel, segAngVel, pose.rotation.radians)

        // Increment segment index
        currentSegmentIndex++

        // Debug
        /*
        System.out.printf(
                "[Path Follower] V: %2.3f, A: %2.3f, X Error: %2.3f, Y Error: %2.3f, Theta Error: %2.3f, Actual Speed: %2.3f %n",
                v, w, xError, yError, thetaError, (DriveSubsystem.leftEncoder.rate + DriveSubsystem.rightEncoder.rate) / 2
        )
        */

        return Twist2D(v, 0.0, w)
    }

    private fun sinc(theta: Double): Double {
        return if (theta epsilonEquals 0.0) 1.0 - 1.0 / 6.0 * theta * theta
        else sin(theta) / theta
    }

    fun calculateLinearVel(
            xError: Double,
            yError: Double,
            thetaError: Double,
            pathV: Double,
            pathW: Double,
            theta: Double
    ) = (pathV cos thetaError) + (gain(pathV, pathW) * ((xError cos theta) + (yError sin theta)))

    fun calculateAngularVel(
            xError: Double,
            yError: Double,
            thetaError: Double,
            pathV: Double,
            pathW: Double,
            theta: Double
    ) = pathW + kBeta * pathV * sinc(thetaError) * ((yError cos theta) - (xError sin theta)) +
            gain(pathV, pathW) * thetaError

    private fun gain(v: Double, w: Double): Double {
        return 2 * kZeta * sqrt(w.pow(2) + kBeta * v.pow(2))
    }
}
