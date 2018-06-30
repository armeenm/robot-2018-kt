[Robot-2018-KT](../../index.md) / [frc.team4096.engine.motion.util](../index.md) / [PIDVAController](./index.md)

# PIDVAController

`class PIDVAController`

### Constructors

| [&lt;init&gt;](-init-.md) | `PIDVAController(pidvaVals: `[`PIDVAVals`](../-p-i-d-v-a-vals/index.md)`, setpoint: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, pidSourceFun: () -> `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, pidSinkFun: (`[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)`, pidSourceType: PIDSourceType)` |

### Properties

| [derivative](derivative.md) | `var derivative: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [error](error.md) | `var error: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [integral](integral.md) | `var integral: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [isEnabled](is-enabled.md) | `var isEnabled: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [lastError](last-error.md) | `var lastError: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [pidSinkFun](pid-sink-fun.md) | `val pidSinkFun: (`[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`) -> `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [pidSourceFun](pid-source-fun.md) | `val pidSourceFun: () -> `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [pidSourceType](pid-source-type.md) | `val pidSourceType: PIDSourceType` |
| [pidvaVals](pidva-vals.md) | `val pidvaVals: `[`PIDVAVals`](../-p-i-d-v-a-vals/index.md) |
| [setpoint](setpoint.md) | `var setpoint: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |

### Functions

| [disable](disable.md) | `fun disable(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [enable](enable.md) | `fun enable(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

