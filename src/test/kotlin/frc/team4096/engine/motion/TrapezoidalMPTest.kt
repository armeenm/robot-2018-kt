package frc.team4096.engine.motion

import frc.team4096.engine.motion.util.PVAJData
import frc.team4096.robot.drivetrain.DriveConsts
import org.junit.jupiter.api.Test
import org.knowm.xchart.QuickChart
import org.knowm.xchart.SwingWrapper
import kotlin.math.pow
import org.knowm.xchart.style.Styler.ChartTheme
import org.knowm.xchart.XYChartBuilder
import org.knowm.xchart.XYChart




class TrapezoidalMPTest {
	@Test
	fun test() {
		// Chart
		val velData = arrayListOf<Double>()
		val posData = arrayListOf<Double>()
		val tData = arrayListOf<Double>()

		// Motion Profile
		val targetPos = 50.0
		var pos = 0.0
		val freq = 5.0
		val dt = 1 / freq
		var time = 0.0
		val tmp = TrapezoidalMP(targetPos, DriveConsts.MAX_VEL, DriveConsts.MAX_ACCEL, { Double.NaN }, {}, freq)
		var pvajData = PVAJData()

		while (pos < targetPos) {
			pos += pvajData.vel * dt + 0.5 * pvajData.accel * dt.pow(2)
			println(pos)
			time += dt
			pvajData = tmp.follow(pos)
			velData.add(pvajData.vel)
			posData.add(pvajData.pos)
			tData.add(time)
		}

		println("tAccel: ${tmp.tAccel}")
		println("xAccel: ${tmp.xAccel}")
		println("tCruise: ${tmp.tCruise}")
		println("xCruise: ${tmp.xCruise}")

		val chart = XYChartBuilder()
			.width(800)
			.height(600)
			.title("Trapezoidal MP")
			.xAxisTitle("Time")
			.yAxisTitle("Y")
			.build()

		chart.addSeries("Velocity", tData, velData)
		//chart.addSeries("Position", tData, posData)

		/*
		val chart = QuickChart.getChart(
			"Trapezoidal MP",
			"Time",
			"Velocity",
			"Motion",
			tData,
			velData
		)
		*/

		SwingWrapper(chart).displayChart()
		Thread.sleep(1000000)
	}
}