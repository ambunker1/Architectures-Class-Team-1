/**
 * Only selected measurement types will pass through its output pipe. Non-selected
 * measurements will be dropped.
 * @author team-one
 */
public class DataSelectionFilter extends AbstractMeasurementFilter {

  private boolean[] selectedData;

  public DataSelectionFilter(MeasurementId... selections) {
    selectedData = new boolean[MeasurementId.values().length];
    for (MeasurementId selection : selections) {
      selectedData[selection.ordinal()] = true;
    }
  }

  /** {@inheritDoc} */
  @Override
  protected byte[] doFilter(Measurement argMeasurement) {
    if (selectedData[argMeasurement.getId()]) {
      return argMeasurement.toByteArray();
    }
    else {
      return null;
    }
  }

}
