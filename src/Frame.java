import java.nio.ByteBuffer;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A logical model for a frame.
 * 
 * @author team-one
 */
public class Frame {

  private Queue<Measurement> measurements = new LinkedList<Measurement>();

  /**
   * Add a measurement into the current frame.
   * 
   * @param argMeasurement
   *          a measurement to be added
   */
  public void addMeasurement(Measurement argMeasurement) {
    measurements.add(argMeasurement);
  }

  /**
   * Returns measurements it holds
   * 
   * @return measurements
   */
  public Queue<Measurement> getMeasurements() {
    return measurements;
  }

  /**
   * Checks if frame contains measurement type.
   * 
   * @param argId
   *          measurement id/type
   * @return true if frame contains measurement type/id, false otherwise
   */
  public boolean hasMeasurement(MeasurementId argId) {
    for (Measurement m : measurements) {
      if (m.getMeasurementId() == argId) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the measurement that matches given type/id.
   * 
   * @param argId
   *          measure type/id
   * @return measurement that matches given type/id
   */
  public Measurement getMeasurement(MeasurementId argId) {
    for (Measurement m : measurements) {
      if (m.getMeasurementId() == argId) {
        return m;
      }
    }
    return null;
  }

  /**
   * Sets the value of the measurement that matches the given type/id
   * 
   * @param argId
   *          type/id of the measurement
   * @param argValue
   *          value to be set on the measurement type/id
   */
  public void setMeasurement(MeasurementId argId, double argValue) {
    for (Measurement m : measurements) {
      if (m.getMeasurementId() == argId) {
        m.setValue(argValue);
        break;
      }
    }
  }

  /**
   * Converts current frame into byte array.
   * 
   * @return byte array representation of the frame
   */
  public byte[] toByteArray() {
    ByteBuffer buffer = ByteBuffer.allocate(measurements.size() * Measurement.BYTESIZE);
    for (Measurement m : measurements) {
      buffer.put(m.toByteArray());
    }
    buffer.flip();
    return buffer.array();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Measurement: [ ");

    for (Measurement m : measurements) {
      if (m.getMeasurementId() == MeasurementId.TIME) {
        Date date = new Date();
        sb.append("time: ");
        try {
          date = new Date(m.getValue().longValue());
        } catch (NumberFormatException nfe) {
          System.err.println("Failed to convert " + m.getValue() + " to milliseconds.");
        }
        sb.append(FrameReader.DATE_FORMATTER.format(date));
      } else {
        sb.append(", ");
        sb.append(m.toString());
      }
    }

    return sb.toString().replaceAll(", $", "").concat(" ]");
  }

}
