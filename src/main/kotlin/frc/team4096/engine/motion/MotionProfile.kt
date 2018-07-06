package frc.team4096.engine.motion

import frc.team4096.engine.motion.util.PVAJData

abstract class MotionProfile {
	abstract val source: () -> Double
	abstract val sink: (Double) -> Unit
	abstract val isFinished: Boolean

	abstract fun follow(curPos: Double): PVAJData
}
