import java.nio.ByteBuffer;
import java.util.Arrays;
/**
 * Logical model for a measurement.
 * @author team-one
 */
public class Measurement {

  public static final int BYTESIZE = 12;
  private int id;
  private Double value;
  private int num = 1;

  
  public Measurement(byte[] argMeasurementBytes) {
	 String outstring = "";
	 for(byte b : argMeasurementBytes){
		 outstring = outstring + String.format("%02x", b);
	 }
	 System.out.println("Creating measurement object " + num + ". Bytestring is " + outstring);
	 num++;
    ByteBuffer buff = ByteBuffer.wrap(argMeasurementBytes);
    id = buff.getInt();
    if(id==MeasurementId.TIME.ordinal()){
    	
    	//value = (double) buff.getInt();
    	Long y = buff.getLong();

    	System.out.println("ms value is " + y);
    	value = y.doubleValue();

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
