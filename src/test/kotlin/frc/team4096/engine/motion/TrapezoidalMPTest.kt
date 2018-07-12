package frc.team4096.engine.motion

import frc.team4096.engine.motion.util.PVAJData
import frc.team4096.robot.drivetrain.DriveConsts
import org.junit.jupiter.api.Test
import org.knowm.xchart.SwingWrapper
import kotlin.math.pow
import org.knowm.xchart.XYChartBuilder

class TrapezoidalMPTest {
	@Test
	fun test() {
		// Chart
		val velData = arrayListOf<Double>()
		val posData = arrayListOf<Double>()
		val tData = arrayListOf<Double>()

		// Motion Profile
		val targetPos = 20.0
		var pos = 0.0
		val freq = 50.0
		val dt = 1 / freq
		var time = 0.0
		val tmp = TrapezoidalMP(targetPos, DriveConsts.MAX_VEL, DriveConsts.MAX_ACCEL, { Double.NaN }, {}, freq)
		var pvajData: PVAJData

		println("tAccel: ${tmp.tAccel}")
		println("xAccel: ${tmp.xAccel}")
		println("tCruise: ${tmp.tCruise}")
		println("xCruise: ${tmp.xCruise}")
		println("Total Distance: ${tmp.xCruise + 2 * tmp.xAccel}")

		do {
			pvajData = tmp.follow(pos)
			time += dt
			//pos += pvajData.vel * dt + 0.5 * pvajData.accel * dt.pow(2)
			pos = pvajData.pos
			println(pos)
			velData.add(pvajData.vel)
			posData.add(pvajData.pos)
			tData.add(time)
		} while (tmp.state != TrapezoidalMP.ProfileState.REST)

		println(pos)

		val chart = XYChartBuilder()
			.width(800)
			.height(600)
			.title("Trapezoidal MP")
			.xAxisTitle("Time (seconds)")
			.yAxisTitle("Y")
			.build()

		chart.addSeries("Velocity", tData, velData)
		chart.addSeries("Position", tData, posData)

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