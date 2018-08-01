package frc.team4096.engine.extensions

operator fun Pair<Double, Double>.minus(other: Pair<Double, Double>) =
	Pair(first - other.first, second - other.second)

operator fun Pair<Double, Double>.plus(other: Pair<Double, Double>) =
	Pair(first + other.first, second + other.second)

operator fun Pair<Double, Double>.times(other: Double) = Pair(first * other, second * other)
operator fun Pair<Double, Double>.div(other: Double) = this.times(1 / other)
