[Robot-2018-KT](../../index.md) / [frc.team4096.robot.subsystems](../index.md) / [ElevatorSubsystem](./index.md)

# ElevatorSubsystem

`object ElevatorSubsystem : `[`ZedSubsystem`](../../frc.team4096.engine.wpi/-zed-subsystem/index.md)

### Types

| [ElevatorState](-elevator-state/index.md) | `enum class ElevatorState` |

### Properties

| [controlState](control-state.md) | `var controlState: `[`ControlState`](../../frc.team4096.engine.motion.util/-control-state/index.md) |
| [hwState](hw-state.md) | `var hwState: `[`ElevatorState`](-elevator-state/index.md) |
| [masterMotor](master-motor.md) | `var masterMotor: WPI_TalonSRX` |
| [slaveMotor](slave-motor.md) | `var slaveMotor: WPI_VictorSPX` |
| [speed](speed.md) | `var speed: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html) |

### Functions

| [initDefaultCommand](init-default-command.md) | `fun initDefaultCommand(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [log](log.md) | `fun log(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [reset](reset.md) | `fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inherited Functions

| [autoLog](../../frc.team4096.engine.wpi/-zed-subsystem/auto-log.md) | `open fun autoLog(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [autoReset](../../frc.team4096.engine.wpi/-zed-subsystem/auto-reset.md) | `open fun autoReset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [teleopLog](../../frc.team4096.engine.wpi/-zed-subsystem/teleop-log.md) | `open fun teleopLog(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [teleopReset](../../frc.team4096.engine.wpi/-zed-subsystem/teleop-reset.md) | `open fun teleopReset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

