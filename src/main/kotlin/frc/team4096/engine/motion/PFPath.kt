package frc.team4096.engine.motion

import com.google.gson.Gson
import frc.team4096.robot.misc.MiscConsts
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
	private val pathName: String,
	private val baseFilePath: String = MiscConsts.PF_HOME
) {

	/**
	 * Secondary constructor if not deserializing.
	 */
	constructor(
		pathData: metadata,
		pathName: String,
		baseFilePath: String = MiscConsts.PF_HOME
	) : this(pathName, baseFilePath) {
		this.pathData = pathData
	}

	// You MUST either generate the trajectory or deserialize it from something!
	lateinit var trajectory: Trajectory
	lateinit var modifier: TankModifier
	private var pathData: metadata? = null

	// Pathfinder trajectory CSV
	private val csvFile = File("$baseFilePath/$pathName/trajectory.csv")
	// Associated Pathfinder metadata JSON
	private val jsonFile = File("$baseFilePath/$pathName/metadata.json")

	/**
	 * Generate trajectory and modifier.
	 */
	fun generate() {
		trajectory = Pathfinder.generate(pathData?.waypoints, pathData?.trajectoryConf)
		modifier = TankModifier(trajectory).modify(pathData!!.wheelbaseWidth)
	}

	/**
	 * Deserialize trajectory and other data from CSV and JSON.
	 */
	fun deserialize() {
		// JSON
		val gson = Gson()
		val bufferedReader = jsonFile.bufferedReader()
		val jsonText = bufferedReader.use { it.readText() }
		pathData = gson.fromJson(jsonText, metadata::class.java)

		// CSV
		trajectory = Pathfinder.readFromCSV(csvFile)
		modifier = TankModifier(trajectory).modify(pathData!!.wheelbaseWidth)
	}

	/**
	 * Serialize trajectory, config, and waypoints.
	 * NOT null safe. Only call after generate or deserialize.
	 * @param csvName Filename to save trajectory to
	 */
	fun serialize() {
		// CSV
		Pathfinder.writeToCSV(csvFile, trajectory)

		// JSON
		val gson = Gson()
		val jsonStr = gson.toJson(pathData)
		jsonFile.writeText(jsonStr)
	}

	data class metadata(
		val waypoints: Array<Waypoint>,
		val trajectoryConf: Trajectory.Config,
		val wheelbaseWidth: Double
	)
}
