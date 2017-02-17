import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A utility that reads bytes one at a time and builds Measurement model out of 
 * the byte sequence. Holds the measurements until they get polled out.
 * @author team-one
 */
public class MeasurementReader {

  private ByteBuffer buffer = ByteBuffer.allocate(Measurement.BYTESIZE);
  // LinkedList is O(1) for Queue.add, Queue.poll, and Queue.peek operations.
  private Queue<Measurement> measurements = new LinkedList<Measurement>();

  /**
   * Feed the reader a byte.
   * @param argByte byte to feed
   */
  public void put(byte argByte) {
    buffer.put(argByte);
    // buffer is now full
    if (!buffer.hasRemaining()) {
      byte[] b1= buffer.array();
      Measurement m = new Measurement(b1);
      measurements.add(m);
      buffer.clear();
    }
  }

  /**
   * Checks if a measurement is available.
   * @return true if available, false otherwise
   */
  public boolean hasMeasurementAvailable() {
    return measurements.size() > 0;
  }

  /**
   * Returns count of the measurements available
   * @return count of the measurements available
   */
  public int getAvailableMeasurementCount() {
    return measurements.size();
  }

  /**
   * Polls the first measurement
   * @return the first measurement
   */
  public Measurement pollMeasurement() {
    // throw NPE if the queue is empty.
    return measurements.poll();
  }

}
