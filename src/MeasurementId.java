
public enum MeasurementId {

	TIME(0),
	VELOCITY(1),
	ALTITUDE(2),
	PRESSURE(3),
	TEMPERATURE(4),
	ATTITUDE(5);
	
	
	MeasurementId(int argType) {
		type = argType;
	}

	private int type;
	
	public static MeasurementId getId(int argType) {
		if (argType == 0) {
			return TIME;
		} 
		else if (argType == 1) {
			return VELOCITY;
		}
		else if (argType == 2) {
			return ALTITUDE;
		}
		else if (argType == 3) {
			return MeasurementId.PRESSURE;
		}
		else if (argType == 4) {
			return TEMPERATURE;
		}
		else if (argType == 5) {
			return ATTITUDE;
		}
		else {
			return null;
		}
	}
	
	public int getType() {
		return type;
	}
	
	public String getName() {
		return this.name().toLowerCase();
	}

}
