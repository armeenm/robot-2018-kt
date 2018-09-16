package frc.team4096.engine.motion

import frc.team4096.engine.kinematics.Pose2D
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
        System.setProperty("java.library.path", "/usr/local")
        val xDataFollower = arrayListOf(0.0)
        val yDataFollower = arrayListOf(0.0)
        val xDataPath = arrayListOf(0.0)
        val yDataPath = arrayListOf(0.0)

        val pathMetadata = PFPath.Metadata(arrayOf(
                Waypoint(0.0, 0.0, 0.0),
                Waypoint(10.0, 0.0, 0.0)
        ), Trajectory.Config(
                Trajectory.FitMethod.HERMITE_CUBIC,
                Trajectory.Config.SAMPLES_HIGH,
                0.05,
                DriveConsts.DT_MAX_VEL, DriveConsts.DT_MAX_ACCEL, DriveConsts.DT_MAX_JERK
        ), DriveConsts.DT_TRACK_WIDTH
        )
        val path = PFPath(pathMetadata, "")
        path.generate()

        val ramseteFollower = RamseteFollower(path.trajectory!!, DriveConsts.BETA, DriveConsts.ZETA)

        var pose = Pose2D()
        val freq = 50.0
        val dt = 1 / freq
        var time = 0.0

        println(ramseteFollower.trajectory.segments.size)

        while (!ramseteFollower.isFinished) {
            val twist = ramseteFollower.update(pose)
            val heading = twist.dtheta * dt
            val pos = twist.dx * dt
            val dx = pos * cos(pose.rotation.radians + heading)
            val dy = pos * sin(pose.rotation.radians + heading)

            pose = Pose2D(pose.translation.x + dx, pose.translation.y + dy)

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

        chart.addSeries("Path", xDataPath, yDataPath)
        chart.addSeries("Follower", xDataFollower, yDataFollower)

        SwingWrapper(chart).displayChart()
        Thread.sleep(1000000)
    }
}