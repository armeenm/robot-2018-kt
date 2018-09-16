package frc.team4096.engine.motion

import frc.team4096.engine.kinematics.Pose2D
import frc.team4096.engine.math.boundRadians
import frc.team4096.robot.drivetrain.DriveConsts
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint
import org.junit.jupiter.api.Test
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder
import kotlin.math.cos
import kotlin.math.sin

class RamseteTest {
    @Test
    fun test() {
        val xDataFollower = arrayListOf<Double>()
        val yDataFollower = arrayListOf<Double>()
        val xDataPath = arrayListOf<Double>()
        val yDataPath = arrayListOf<Double>()

        val waypointArray = arrayOf(
                Waypoint(-1.0, -1.0, 0.0),
                Waypoint(10.0, 5.0, 0.0)
        )

        val kBeta = 0.6
        val kZeta = 0.5

        var pose = Pose2D(waypointArray[0].x, waypointArray[0].y, waypointArray[0].angle)
        val freq = 50.0
        val dt = 1 / freq
        var time = 0.0

        val pathMetadata = PFPath.Metadata(
                waypointArray,
                Trajectory.Config(
                        Trajectory.FitMethod.HERMITE_CUBIC,
                        Trajectory.Config.SAMPLES_HIGH,
                        dt,
                        DriveConsts.DT_MAX_VEL, DriveConsts.DT_MAX_ACCEL, DriveConsts.DT_MAX_JERK
                ),
                DriveConsts.DT_TRACK_WIDTH
        )
        val path = PFPath(pathMetadata, "")
        path.generate()

        val ramseteFollower = RamseteFollower(path.trajectory!!, kBeta, kZeta)

        println(ramseteFollower.trajectory.segments.size)

        while (!ramseteFollower.isFinished) {
            val twist = ramseteFollower.update(pose)
            val heading = twist.dtheta * dt
            val pos = twist.dx * dt
            val dx = pos * cos(pose.rotation.radians + heading)
            val dy = pos * sin(pose.rotation.radians + heading)

            pose = Pose2D(pose.translation.x + dx, pose.translation.y + dy, (pose.rotation.radians + heading).boundRadians())

            xDataFollower.add(pose.translation.x)
            yDataFollower.add(pose.translation.y)
            xDataPath.add(ramseteFollower.currentSegment.x)
            yDataPath.add(ramseteFollower.currentSegment.y)

            time += dt
        }

        val chart = XYChartBuilder()
                .width(800)
                .height(600)
                .title("RAMSETE Follower Test")
                .xAxisTitle("X Position (Feet)")
                .yAxisTitle("Y Position (Feet)")
                .build()
                .apply {
                    addSeries("Path", xDataPath, yDataPath)
                    addSeries("Follower", xDataFollower, yDataFollower)
                }

        SwingWrapper(chart).displayChart()
        Thread.sleep(1000000)
    }
}