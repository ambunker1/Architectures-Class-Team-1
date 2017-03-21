/**
 * Measurement id enumeration.
 * @author team-one
 */
public enum MeasurementId {

  TIME(0), VELOCITY(1), ALTITUDE(2), PRESSURE(3), TEMPERATURE(4), ATTITUDE(5);

  private int id;
  
  MeasurementId(int argType) {
    id = argType;
  }

  /**
   * Returns measurement id enum based on the id supplied.
   * @param argId if of the measurement id enum
   * @return measurement id enum
   */
  public static MeasurementId getId(int argId) {
    MeasurementId result = null;
    for (MeasurementId mid : values()) {
      if (mid.id == argId) {
        result = mid;
        break;
      }
    }
    return result;
  }

  /**
   * Returns measure id name, such as time, velocity, altitude.
   * @return measurement id name
   */
  public String getName() {
    return this.name().toLowerCase();
  }

}
