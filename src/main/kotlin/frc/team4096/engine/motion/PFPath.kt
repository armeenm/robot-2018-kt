package frc.team4096.engine.motion

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.JsonObject
import jaci.pathfinder.Pathfinder
import jaci.pathfinder.Trajectory
import jaci.pathfinder.Waypoint
import jaci.pathfinder.modifiers.TankModifier
import java.io.File

/**
 * Class to handle Pathfinder trajectories and serialization.
 * @param wheelbaseWidth Width of robot wheelbase
 * @param pathName Name of path, e.g. "CS_L"
 * @param baseFilePath Where to store all the path data
 * @constructor Constructs a shell of a Pathfinder path/trajectory
 */
class PFPath(
	val wheelbaseWidth: Double,
	val pathName: String,
	val baseFilePath: String = "/home/lvuser/paths"
) {
	/**
	 * Constructor if no deserialization.
	 * @param waypoints Array of waypoints to use in trajectory
	 * @param trajectoryConf Pathfinder trajectory configuration object
	 * @param wheelbaseWidth Width of robot wheelbase
	 * @param pathName Name of path, e.g. "CS_L"
	 * @param baseFilePath Where to store all the path data, defaults to '/home/lvuser/paths'
	 */
	constructor(
		waypoints: Array<Waypoint>,
		trajectoryConf: Trajectory.Config,
		wheelbaseWidth: Double,
		pathName: String,
		baseFilePath: String
	) : this(wheelbaseWidth, pathName, baseFilePath) {
		this.trajectoryConf = trajectoryConf
		this.waypoints = waypoints
	}

	// You MUST either generate the trajectory or deserialize it from something!
	lateinit var trajectory: Trajectory
	lateinit var modifier: TankModifier
	lateinit var waypoints: Array<Waypoint>
	lateinit var trajectoryConf: Trajectory.Config
	lateinit var jsonData: JsonObject

	// Pathfinder trajectory CSV
	private val csvFile = File("$baseFilePath/$pathName/trajectory.csv")
	// Associated Pathfinder data JSON
	private val jsonFile = File("$baseFilePath/$pathName/data.json")

	/**
	 * Generate trajectory and modifier.
	 */
	fun generate() {
		trajectory = Pathfinder.generate(waypoints, trajectoryConf)
		modifier = TankModifier(trajectory).modify(wheelbaseWidth)
	}

	/**
	 * Deserialize trajectory and other data from CSV and JSON.
	 */
	fun deserialize() {
		// CSV
		trajectory = Pathfinder.readFromCSV(csvFile)
		modifier = TankModifier(trajectory).modify(wheelbaseWidth)

		// JSON
		val bufferedReader = jsonFile.bufferedReader()
		jsonData = bufferedReader.use { it.readText() }
	}

	/**
	 * Serialize trajectory, config, and waypoints.
	 * NOT null safe. Only call after generate or deserialize.
	 * @param csvName Filename to save trajectory to
	 */
	fun serialize() {
		Pathfinder.writeToCSV(csvFile, trajectory)

		jsonData = jsonObject(
			"waypoints" to waypoints,
			"wheelbaseWidth" to wheelbaseWidth,
			"trajectoryConf" to trajectoryConf
		)
		jsonFile.writeText(jsonData.toString())
	}
}
