package frc.team4096.robot.elevator

object ElevatorConsts {
    // Deadband
    const val DEADBAND = 0.05

    // Motors
    const val CAN_MASTER_MOTOR = 1
    const val CAN_SLAVE_MOTOR = 3

    // Inversion
    const val MASTER_INVERTED = false
    const val SLAVE_INVERTED = false

    // Pneumatics
    const val PCM_BRAKE_1 = 1
    const val PCM_BRAKE_2 = 6

    // Sensors
    const val DIN_LIMIT_SW = 4

    // Hardware
    const val ENC_TICKS_PER_REV = 4096
    const val ENC_TICKS_PER_FOOT = 141000
    const val MAX_OPEN_LOOP_SPEED = 0.6
    // Voltage to consider as "full" for voltage compensation.
    const val COMP_VOLTAGE = 12.0

    // Software
    enum class Positions(val index: Int, val pos: Double) {
        BOTTOM(0, 0.0),
        NO_DRAG(1, 0.25),
        SWITCH(2, 2.0),
        SCALE(3, 6.5);

        companion object {
            private val map = Positions.values().associateBy(Positions::index)
            fun fromIndex(index: Int) = map[index]
        }
    }

    const val K_SLOT_ID = 0
    const val DISTANCE_kF = 0.2
    const val DISTANCE_kP = 0.2
    const val DISTANCE_kI = 0.0
    const val DISTANCE_kD = 0.0
    const val MAX_CLOSED_LOOP_ERROR = 5000
    const val MAX_ACCEL = 125000
    const val MAX_CRUISE_VEL = 200000
    const val SENSOR_PHASE = true
}