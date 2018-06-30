[Robot-2018-KT](../../index.md) / [frc.team4096.engine.motion](../index.md) / [TrapezoidalMP](./index.md)

# TrapezoidalMP

`class TrapezoidalMP`

A trapezoidal motion profile.
Consists of vel ramp up, cruise, and vel ramp down periods.
For more information, refer to 254 champs video.

### Parameters

`targetPos` - Target position

`maxVel` - Maximum velocity

`maxAccel` - Maximum acceleration

### Constructors

| [&lt;init&gt;](-init-.md) | `TrapezoidalMP(targetPos: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, maxVel: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, maxAccel: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`)`<br>Constructor |

### Properties

| [curTime](cur-time.md) | `var curTime: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [error](error.md) | `var error: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [goalPos](goal-pos.md) | `var goalPos: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [goalVel](goal-vel.md) | `var goalVel: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [integral](integral.md) | `var integral: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [isFinished](is-finished.md) | `var isFinished: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [maxAccel](max-accel.md) | `val maxAccel: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Maximum acceleration |
| [maxVel](max-vel.md) | `val maxVel: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Maximum velocity |
| [startTime](start-time.md) | `var startTime: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [tAccel](t-accel.md) | `val tAccel: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [tCruise](t-cruise.md) | `val tCruise: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [targetPos](target-pos.md) | `val targetPos: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Target position |
| [xAccel](x-accel.md) | `val xAccel: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [xCruise](x-cruise.md) | `val xCruise: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |

### Functions

| [follow](follow.md) | `fun follow(pidfVals: `[`PIDFVals`](../../frc.team4096.engine.motion.util/-p-i-d-f-vals/index.md)`, sourceFun: () -> `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, sinkFun: (`[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [initFollower](init-follower.md) | `fun initFollower(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

