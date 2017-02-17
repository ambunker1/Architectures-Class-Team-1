/**
 * A filter that converts feet to meter for altitude measurement. 
 * @author team-one
 */
public class FeetToMeterFilter extends AbstractMeasurementFilter {
	private int num = 1;
  /** {@inheritDoc} */
  @Override
  protected byte[] doFilter(Measurement argMeasurement) {
    if (MeasurementId.ALTITUDE == argMeasurement.getMeasurementId()) {
      // convert fahrenheit to celsius
      argMeasurement.setValue(argMeasurement.getValue() / 0.3048);
    }
    byte[] b = argMeasurement.toByteArray();
    String outstring = "";
    for (byte b1 : b){
    	outstring = outstring + String.format("%02x", b1);
    }
    System.out.println("In feet to meter, writing line " + num +" : " + outstring);
    num++;
    return b;
    //return argMeasurement.toByteArray();
  }

}
