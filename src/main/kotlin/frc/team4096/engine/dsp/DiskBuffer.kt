package frc.team4096.engine.dsp;

import java.io.File

class DiskBuffer(val size: Int, val file : File) {
    private val buffer = ArrayList<String>(size)

    fun add(element: String) {
        buffer.add(0, element)
        if (buffer.size >= (size - 1)) {
            //this print writer auto-flushes every line,
            //but ideally we want to flush after all the lines are done
            file.printWriter().use { out ->
                for(line in buffer) {
                    out.println(line)
                }
                buffer.clear()
            }
        }
    }
}
