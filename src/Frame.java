import java.nio.ByteBuffer;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A logical model for a frame.
 * @author team-one
 */
public class Frame {

  private Queue<Measurement> measurements = new LinkedList<Measurement>();

  /**
   * Add a measurement into the current frame.
   * @param argMeasurement a measurement to be added
   */
  public void addMeasurement(Measurement argMeasurement) {
    measurements.add(argMeasurement);
  }

  /**
   * Returns measurements it holds
   * @return measurements
   */
  public Queue<Measurement> getMeasurements() {
    return measurements;
  }
  
  public boolean HasMeasurement(MeasurementId id){
	  for (Measurement m : measurements){
		  if(m.getMeasurementId()==id){
			  return true;
		  }
	  }
	  return false;
  }
  
  public Measurement GetMeasurement(MeasurementId id){
	  for(Measurement m: measurements){
		  if(m.getMeasurementId()==id){
			  return m;
		  }
	  }
	  return null;
  }
  
  public void SetMeasurement(MeasurementId id, double value){
	  for(Measurement m: measurements){
		  if(m.getMeasurementId()==id){
			  m.setValue(value);
			  break;
		  }
	  }
  }

  /**
   * Converts current frame into byte array.
   * @return byte array representation of the frame
   */
  public byte[] toByteArray() {
    ByteBuffer buffer = ByteBuffer.allocate(measurements.size() * Measurement.BYTESIZE);
    for (Measurement m : measurements) {
      buffer.put(m.toByteArray());
    }
    return buffer.array();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    Calendar cal = Calendar.getInstance();
    StringBuilder sb = new StringBuilder();
    sb.append("Measurement: [ ");
    for (Measurement m : measurements) {
      if (m.getMeasurementId() == MeasurementId.TIME) {
        sb.append("time: ");
        try {
          cal.setTimeInMillis(Long.valueOf(m.getValue().toString()));  
        } catch (NumberFormatException nfe) {
          System.err.println("Failed to convert " + m.getValue() + " to milliseconds.");
        }
        sb.append(FrameReader.DATE_FORMATTER.format(cal.getTime()));
      } else {
        sb.append(", ");
        sb.append(m.toString());
      }
    }

    return sb.toString().replaceAll(", $", "").concat(" ]");
  }

}
