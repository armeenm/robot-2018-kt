package frc.team4096.engine.motion

import frc.team4096.engine.motion.util.PVAJData
import frc.team4096.robot.drivetrain.DriveConsts
import org.junit.jupiter.api.Test
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import kotlin.math.pow


class TrapezoidalMPTest {
	@Test
	fun test() {
		// Chart
		val xData = arrayListOf<Double>()
		val yData = arrayListOf<Double>()

		// Motion Profile
		var pos = 0.0
		val dt = 1 / 50.0
		var time = 0.0
		val tmp = TrapezoidalMP(100.0, DriveConsts.MAX_VEL, DriveConsts.MAX_ACCEL, { Double.NaN }, {})
		var pvajData = PVAJData()

		while (pos <= 100.0) {
			pos += pvajData.vel * dt + 0.5 * pvajData.accel * dt.pow(2)
			time += dt
			pvajData = tmp.follow(pos)
			yData.add(pvajData.vel)
			xData.add(time)
		}

		val chart = QuickChart.getChart(
			"Trapezoidal MP",
			"Time",
			"Velocity",
			"Motion",
			xData,
			yData
		)

		SwingWrapper(chart).displayChart()
		Thread.sleep(1000000)
	}
}