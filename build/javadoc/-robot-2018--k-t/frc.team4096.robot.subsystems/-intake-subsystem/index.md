[Robot-2018-KT](../../index.md) / [frc.team4096.robot.subsystems](../index.md) / [IntakeSubsystem](./index.md)

# IntakeSubsystem

`object IntakeSubsystem : `[`ZedSubsystem`](../../frc.team4096.engine.wpi/-zed-subsystem/index.md)

### Types

| [HasCube](-has-cube/index.md) | `enum class HasCube` |
| [SqueezeState](-squeeze-state/index.md) | `enum class SqueezeState` |

### Properties

| [cube](cube.md) | `var cube: `[`HasCube`](-has-cube/index.md) |
| [intakeSpeed](intake-speed.md) | `var intakeSpeed: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [isIntakeStalling](is-intake-stalling.md) | `var isIntakeStalling: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [isRotateStalling](is-rotate-stalling.md) | `var isRotateStalling: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [rotateHolding](rotate-holding.md) | `var rotateHolding: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [rotateSpeed](rotate-speed.md) | `var rotateSpeed: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |
| [rotationControlState](rotation-control-state.md) | `var rotationControlState: `[`ControlState`](../../frc.team4096.engine.motion.util/-control-state/index.md) |
| [squeeze](squeeze.md) | `var squeeze: `[`SqueezeState`](-squeeze-state/index.md) |
| [wheelControlState](wheel-control-state.md) | `var wheelControlState: `[`ControlState`](../../frc.team4096.engine.motion.util/-control-state/index.md) |

### Functions

| [autoReset](auto-reset.md) | `fun autoReset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [initDefaultCommand](init-default-command.md) | `fun initDefaultCommand(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [log](log.md) | `fun log(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [reset](reset.md) | `fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inherited Functions

| [autoLog](../../frc.team4096.engine.wpi/-zed-subsystem/auto-log.md) | `open fun autoLog(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [teleopLog](../../frc.team4096.engine.wpi/-zed-subsystem/teleop-log.md) | `open fun teleopLog(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [teleopReset](../../frc.team4096.engine.wpi/-zed-subsystem/teleop-reset.md) | `open fun teleopReset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

