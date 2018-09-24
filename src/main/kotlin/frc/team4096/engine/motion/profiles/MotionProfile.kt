package frc.team4096.engine.motion.profiles

import frc.team4096.engine.motion.PVAJData

abstract class MotionProfile {
    abstract val source: () -> Double
    abstract val sink: (Double) -> Unit
    abstract val isFinished: Boolean
    open val freq = 50.0

    abstract fun follow(curPos: Double): PVAJData
    abstract fun reset()
}
