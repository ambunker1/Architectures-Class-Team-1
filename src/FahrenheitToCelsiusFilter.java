/**
 * A filter that converts fahrenheit to celsius for temperature measurement.
 * @author team-one
 */
public class FahrenheitToCelsiusFilter extends AbstractMeasurementFilter {

  /** {@inheritDoc} */
  @Override
  protected byte[] doFilter(Measurement argMeasurement) {
    if (MeasurementId.TEMPERATURE == argMeasurement.getMeasurementId()) {
      argMeasurement.setValue(((argMeasurement.getValue()) * 5) / 9);  
    }
    return argMeasurement.toByteArray();
  }

}
