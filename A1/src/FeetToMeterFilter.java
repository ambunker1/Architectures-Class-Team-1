/**
 * A filter that converts feet to meter for altitude measurement. 
 * @author team-one
 */
public class FeetToMeterFilter extends AbstractMeasurementFilter {
  /** {@inheritDoc} */
  @Override
  protected byte[] doFilter(Measurement argMeasurement) {
    if (MeasurementId.ALTITUDE == argMeasurement.getMeasurementId()) {
      // convert feet to meter
      argMeasurement.setValue(argMeasurement.getValue() / 0.3048);
    }
    return argMeasurement.toByteArray();
  }

}
