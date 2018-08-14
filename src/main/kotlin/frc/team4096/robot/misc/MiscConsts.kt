package frc.team4096.robot.misc

object MiscConsts {
    // CAN
    const val CAN_PCM = 2
    const val CAN_PDP = 0

    // Analog
    const val AIN_PRESSURE = 0
    const val PRESSURE_AVG_BITS = 12

    // Software
    const val K_TIMEOUT_MS = 10
    const val K_DT = 0.02 // Seconds
    /**
     * Where trajectories should be stored.
     */
    const val PF_HOME = "/home/lvuser/paths"

    const val RAP_LYRICS = """
	Pressure's weak
	Intake's heavy
	Dirt on his wheels already
	Overload me! (Code's spaghetti)

	But on the outside he looks fierce, Atla-Z
	To carry cubes
	But he keeps on droppin
	When he broke down, the whole crowd groans so loud
	He closes his valve, but still the air leaks out
	He's broken now, Ctrl-Z is mopin' now
	The power's run out, scale's pressin down

	Snap back to the tragedy, oh there goes the battery
	Oh there goes belt, it broke
	He's so bad but he won't be fixed easy, no
	"""
}

