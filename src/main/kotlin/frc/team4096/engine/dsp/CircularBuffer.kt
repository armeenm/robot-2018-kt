package frc.team4096.engine.dsp

/**
 * Circular FIFO buffer.
 * Typically used to smooth out jittery inputs.
 *
 * @param size Size of buffer
 */
class CircularBuffer(val size: Int) {
    private val buffer = ArrayList<Double>(size)
    private var sum = 0.0
    // Number of elements used in the array.
    private var numElements = 0

    /**
     * Take average of buffer.
     */
    val average: Double
        get() {
            return if (numElements == 0)
                0.0
            else
                sum / numElements
        }

    /**
     * Add element to end of buffer.
     */
    fun add(element: Double) {
        sum += element

        if (numElements < (size - 1)) {
            numElements++
        } else {
            sum -= buffer.last()
            buffer.removeAt(size - 1)
        }

        buffer.add(0, element)
    }
}
