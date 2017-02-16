import java.nio.ByteBuffer;
/**
 * Logical model for a measurement.
 * @author team-one
 */
public class Measurement {

  public static final int BYTESIZE = 12;
  private int id;
  private Double value;

  public Measurement(byte[] argMeasurementBytes) {
    ByteBuffer buff = ByteBuffer.wrap(argMeasurementBytes);
    id = buff.getInt();
    if(id==MeasurementId.TIME.ordinal()){

    	//value = (double) buff.getInt();
    	long y = buff.getLong();
    	value = (double) y;
    }
    else{
    value = buff.getDouble();
    }
  }

  /**
   * Returns measurement id, such as 0 (time), 1 (velocity).
   * @return measurement id
   */
  public int getId() {
    return id;
  }

  /**
   * Returns the value of the measurement.
   * @return value of measurement
   */
  public Double getValue() {
    // avoid giving an object reference
    return value.doubleValue();
  }

  /**
   * Sets value of the measurement.
   * @param argValue value to be set
   */
  public void setValue(Double argValue) {
    value = argValue;
  }

  /**
   * Returns measurement id enum
   * @return measurement id enum
   */
  public MeasurementId getMeasurementId() {
    return MeasurementId.getId(id);
  }

  /**
   * Converts measurement into a byte array
   * @return byte array representation of the measurement
   */
  public byte[] toByteArray() {
    ByteBuffer buff = ByteBuffer.allocate(BYTESIZE);
    buff.putInt(id);
    buff.putDouble(value);
    return buff.array();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return getMeasurementId().getName() + ": " + value;
  }

}
