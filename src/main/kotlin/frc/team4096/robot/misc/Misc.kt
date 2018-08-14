package frc.team4096.robot.misc

import edu.wpi.first.wpilibj.CameraServer
import edu.wpi.first.wpilibj.DriverStation
import edu.wpi.first.wpilibj.command.Scheduler

val cameraServer: CameraServer
    get() = CameraServer.getInstance()

val driverStation: DriverStation
    get() = DriverStation.getInstance()

val scheduler: Scheduler
    get() = Scheduler.getInstance()

