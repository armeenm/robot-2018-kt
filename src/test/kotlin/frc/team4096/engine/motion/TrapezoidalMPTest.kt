package frc.team4096.engine.motion

import frc.team4096.robot.drivetrain.DriveConsts
import org.junit.jupiter.api.Test
import org.knowm.xchart.SwingWrapper
import org.knowm.xchart.XYChartBuilder

class TrapezoidalMPTest {
    @Test
    fun test() {
        // Chart
        val posData = arrayListOf<Double>()
        val velData = arrayListOf<Double>()
        val accelData = arrayListOf<Double>()
        val tData = arrayListOf<Double>()

        // Motion Profile
        val targetPos = 30.0
        var pos = 0.0
        val freq = 50.0
        val dt = 1 / freq
        var time = 0.0
        val tmp = TrapezoidalMP(targetPos, DriveConsts.MAX_VEL, DriveConsts.MAX_ACCEL, { Double.NaN }, {}, freq)
        val pvajData1 = PVAJData()
        var pvajData = pvajData1

        do {
            velData.add(pvajData.vel)
            posData.add(pvajData.pos)
            accelData.add(pvajData.accel)
            tData.add(time)
            pvajData = tmp.follow(pos)
            time += dt
            pos = pvajData.pos
        } while (tmp.state != TrapezoidalMP.ProfileState.REST)

        val chart = XYChartBuilder()
                .width(800)
                .height(600)
                .title("Trapezoidal MP")
                .xAxisTitle("Time (seconds)")
                .yAxisTitle("Y")
                .build()

        chart.addSeries("Position", tData, posData)
        chart.addSeries("Velocity", tData, velData)
        chart.addSeries("Acceleration", tData, accelData)

        SwingWrapper(chart).displayChart()
        Thread.sleep(1000000)
    }
}