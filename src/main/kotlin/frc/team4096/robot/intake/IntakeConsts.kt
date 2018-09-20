package frc.team4096.robot.intake

object IntakeConsts {
    // Joystick Deadband
    const val WHEEL_DEAD_BAND = 0.05
    const val ROTATE_DEAD_BAND = 0.05

    // Motors
    const val PWM_WHEELS_MOTOR_1 = 4
    const val PWM_WHEELS_MOTOR_2 = 5
    const val PWM_ROTATE_MOTOR = 6

    // Inversion
    const val WHEELS_1_INVERTED = false
    const val WHEELS_2_INVERTED = false
    const val ROTATION_INVERTED = true

    // PDP
    const val PDP_WHEELS = 7

    // Pneumatics
    const val PCM_SQUEEZE_1 = 2
    const val PCM_SQUEEZE_2 = 5

    // Speeds
    const val MAX_IN_SPEED = 1.0
    const val MAX_OUT_SPEED = -1.0
    const val DEFAULT_ROTATE_SPEED = -0.75

    const val ROTATE_HOLD_SPEED = -0.1
}