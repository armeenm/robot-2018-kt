[Robot-2018-KT](../../index.md) / [frc.team4096.robot.subsystems](../index.md) / [DriveSubsystem](./index.md)

# DriveSubsystem

`object DriveSubsystem : `[`ZedSubsystem`](../../frc.team4096.engine.wpi/-zed-subsystem/index.md)

### Types

| [DriveMode](-drive-mode/index.md) | `enum class DriveMode` |
| [DrivePose](-drive-pose/index.md) | `data class DrivePose` |
| [DriveSignal](-drive-signal/index.md) | `data class DriveSignal` |
| [EncDistances](-enc-distances/index.md) | `data class EncDistances` |
| [GearState](-gear-state/index.md) | `enum class GearState` |

### Properties

| [controlState](control-state.md) | `var controlState: `[`ControlState`](../../frc.team4096.engine.motion.util/-control-state/index.md) |
| [driveMode](drive-mode.md) | `var driveMode: `[`DriveMode`](-drive-mode/index.md) |
| [encDistances](enc-distances.md) | `var encDistances: `[`EncDistances`](-enc-distances/index.md) |
| [gear](gear.md) | `var gear: `[`GearState`](-gear-state/index.md) |
| [isQuickTurn](is-quick-turn.md) | `var isQuickTurn: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |
| [wasCorrecting](was-correcting.md) | `var wasCorrecting: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) |

### Functions

| [curvatureDrive](curvature-drive.md) | `fun curvatureDrive(xSpeed: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, zRotation: `[`Double`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-double/index.html)`, isQuickTurn: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [initDefaultCommand](init-default-command.md) | `fun initDefaultCommand(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [log](log.md) | `fun log(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [periodic](periodic.md) | `fun periodic(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [reset](reset.md) | `fun reset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [stop](stop.md) | `fun stop(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

### Inherited Functions

| [autoLog](../../frc.team4096.engine.wpi/-zed-subsystem/auto-log.md) | `open fun autoLog(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [autoReset](../../frc.team4096.engine.wpi/-zed-subsystem/auto-reset.md) | `open fun autoReset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [teleopLog](../../frc.team4096.engine.wpi/-zed-subsystem/teleop-log.md) | `open fun teleopLog(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [teleopReset](../../frc.team4096.engine.wpi/-zed-subsystem/teleop-reset.md) | `open fun teleopReset(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |

