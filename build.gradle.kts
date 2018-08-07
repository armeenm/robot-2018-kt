plugins {
	java
	idea
	id("edu.wpi.first.GradleRIO") version "2018.06.21"
	id("org.jetbrains.kotlin.jvm") version "1.2.51"
	id("org.jetbrains.dokka") version "0.9.17"
}

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	compile()

}

// GradleRIO //
val TEAM = 4096
val ROBOT_CLASS = "frc.team4096.robot.Robot"
