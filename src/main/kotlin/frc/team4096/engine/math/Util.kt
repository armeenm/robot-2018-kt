package frc.team4096.engine.math

import org.apache.commons.math3.util.Precision.EPSILON
import java.math.BigDecimal
import java.math.BigInteger

/**
 * Function to achieve generics for abs function.
 *
 * @param x Value to abs()
 */
fun <T : Number> genericAbs(x: T): T {
    val absoluteValue: Number = when (x) {
        is Double -> Math.abs(x)
        is Int -> Math.abs(x)
        is Float -> Math.abs(x)
        is BigDecimal -> x.abs()
        is BigInteger -> x.abs()
        else -> throw IllegalArgumentException("Unsupported type ${x.javaClass}")
    }

    @Suppress("UNCHECKED_CAST")
    return absoluteValue as T
}

/**
 * Inline cosine.
 */
infix fun Double.cos(x: Double): Double = this * cos(x)

/**
 * Infix sine.
 */
infix fun Double.sin(x: Double): Double = this * sin(x)

fun avg(vararg nums: Double) = nums.sum() / nums.size

typealias Matrix = org.apache.commons.math3.linear.Array2DRowRealMatrix

/**
 * Clamp extension function for Double's.
 * Binds an input to a min/max.
 */
fun Double.clamp(min: Double, max: Double) =
        when {
            (this < min) -> min
            (this > max) -> max
            else -> this
        }

/**
 * Fuzzy equals to determine if the difference between two numbers is less than EPSILON.
 * Used for floating point comparisons.
 */
infix fun Double.epsilonEquals(other: Double) = (this - other) < EPSILON
