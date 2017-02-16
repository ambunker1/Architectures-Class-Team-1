import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Frame {
	
	private List<Measurement> measurements = new ArrayList<Measurement>();

	public void addMeasurement(Measurement argMeasurement) {
		measurements.add(argMeasurement);
	}
	
	public byte[] toByteArray() {
		ByteBuffer buffer = ByteBuffer.allocate(measurements.size() * Measurement.BYTESIZE);
		for (Measurement m : measurements) {
			buffer.put(m.toByteArray());
		}
		return buffer.array();
	}
	
	@Override
	public String toString() {
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append("Measurement: [ ");
		for (Measurement m : measurements) {
			if (m.getMeasurementId() == MeasurementId.TIME) {
				sb.append("time: ");
				cal.setTimeInMillis(Long.valueOf(m.getValue().toString()));
				sb.append(FrameReader.DATE_FORMATTER.format(cal.getTime()));
			}
			else {
				sb.append(", ");
				sb.append(m.toString());
			}
		}
		
		return sb.toString().replaceAll(", $", " ]");
	}

	public List<Measurement> getMeasurements() {
		return measurements;
	}
}
