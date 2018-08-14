package frc.team4096.engine.math

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D
import org.apache.commons.math3.linear.RealMatrix
import java.lang.Double.sum

//fun Vector2D.rotate(other: Vector2D): Vector2D = (this, other)

operator fun Vector2D.minus(other: Vector2D): Vector2D = Vector2D(this.x - other.x, this.y - other.y)

operator fun Vector2D.plus(other: Vector2D): Vector2D = add(other)

operator fun Vector2D.times(scalar: Double): Vector2D = scalarMultiply(scalar)
operator fun Vector2D.times(other: Vector2D): Double = dotProduct(other)
operator fun Vector2D.times(other: RealMatrix): Vector2D = Vector2D(Matrix(this.toArray()).multiply(other).getRow(0))

operator fun Vector2D.div(scalar: Double): Vector2D = scalarMultiply(1 / scalar)

operator fun Vector2D.unaryPlus(): Vector2D = Vector2D(+this.x, +this.y)
operator fun Vector2D.unaryMinus(): Vector2D = Vector2D(-this.x, -this.y)

val Vector2D.l1: Double
    get() = norm1

val Vector2D.l2: Double
    get() = norm

val Vector2D.atan2: Double
    get() = Math.atan2(this.y, this.x)

val Vector2D.avg: Double
    get() = avg(this.x, this.y)
val Vector2D.sum: Double
    get() = sum(this.x, this.y)

operator fun Vector2D.get(index: Int) =
        if (index == 0) x else y

