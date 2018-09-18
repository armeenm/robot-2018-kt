package frc.team4096.robot.drivetrain.commands

import edu.wpi.first.wpilibj.Timer
import edu.wpi.first.wpilibj.command.Command
import frc.team4096.robot.drivetrain.DriveSubsystem
import org.apache.commons.math3.stat.regression.SimpleRegression

/**
 * Command to characterize the drivetrain.
 * Based on 5190 offseason code.
 */
// TODO: kA calculations with secant line + square root regression.
class CharacterizationCmd : Command() {
    class dtSide(val sideStr: String, private val encoderFun: () -> Double) {
        var percentOut = 0.0
        var vIntercept = 0.0
        val regression = SimpleRegression()
        val dataPoints = ArrayList<Triple<Double, Double, Double>>()
        val speed
            get() = encoderFun()

        fun addPoint() {
            dataPoints.add(Triple(percentOut, encoderFun(), Timer.getFPGATimestamp()))
        }
    }

    val timer = Timer()
    val leftSide = dtSide("Left") { DriveSubsystem.leftEncoder.rate }
    val rightSide = dtSide("Right") { DriveSubsystem.rightEncoder.rate }
    val sides = listOf(leftSide, rightSide)

    init {
        requires(DriveSubsystem)
        isInterruptible = false
    }

    private fun applySpeeds() {
        DriveSubsystem.diffDrive.tankDrive(leftSide.percentOut, rightSide.percentOut)
    }

    override fun initialize() {
        applySpeeds()
        sides.forEach { it.addPoint() }
        timer.start()
    }

    override fun execute() {
        // 1Hz execution
        if (timer.hasPeriodPassed(1.0)) {
            sides.forEach {
                val speed = it.speed
                if (speed > 0.01) {
                    if (it.vIntercept == 0.0) it.vIntercept = it.percentOut
                    it.addPoint()
                    println("Added data point: ${it.percentOut}% to $speed ft/s")
                }
                it.percentOut += 0.25 / 12.0
                applySpeeds()
            }
            timer.reset()
        }
    }

    override fun isFinished() = sides.all { it.percentOut >= 1.0 }

    override fun end() {
        DriveSubsystem.stop()
        sides.forEach {side ->
            side.dataPoints.forEach { point ->
                side.regression.addData(point.first, point.second)
            }
            println("${side.sideStr} side: kV: ${1 / side.regression.slope}, vIntercept: ${side.vIntercept}, Linearity: ${side.regression.rSquare}")
        }
    }
}