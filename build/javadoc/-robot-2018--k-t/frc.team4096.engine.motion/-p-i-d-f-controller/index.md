[Robot-2018-KT](../../index.md) / [frc.team4096.engine.motion](../index.md) / [PIDFController](./index.md)

# PIDFController

`class PIDFController`

Custom asynchronous PID(F) controller.
Proportional, integral, and derivative gains, as well as fixed feed-forward.

### Parameters

`pidfVals` - P, I, D, and F values

`setpoint` - Target setpoint

`pidSourceFun` - Source lambda (e.g. encoder get)

`pidSinkFun` - Sink lambda (e.g. motor controller set)

### Constructors

| [&lt;init&gt;](-init-.md) | `PIDFController(pidfVals: `[`PIDFVals`](../../frc.team4096.engine.motion.util/-p-i-d-f-vals/index.md)`, setpoint: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, pidSourceFun: () -> `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, pidSinkFun: (`[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, pidSourceType: PIDSourceType)`<br>Constructor |

### Properties

| [derivative](derivative.md) | `var derivative: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [error](error.md) | `var error: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [integral](integral.md) | `var integral: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [isEnabled](is-enabled.md) | `var isEnabled: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [lastError](last-error.md) | `var lastError: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [pidSinkFun](pid-sink-fun.md) | `val pidSinkFun: (`[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Sink lambda (e.g. motor controller set) |
| [pidSourceFun](pid-source-fun.md) | `val pidSourceFun: () -> `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Source lambda (e.g. encoder get) |
| [pidSourceType](pid-source-type.md) | `val pidSourceType: PIDSourceType` |
| [pidfVals](pidf-vals.md) | `val pidfVals: `[`PIDFVals`](../../frc.team4096.engine.motion.util/-p-i-d-f-vals/index.md)<br>P, I, D, and F values |
| [setpoint](setpoint.md) | `var setpoint: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)<br>Target setpoint |

### Functions

| [disable](disable.md) | `fun disable(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Disables controller. |
| [enable](enable.md) | `fun enable(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Enables controller and launches coroutine. |

