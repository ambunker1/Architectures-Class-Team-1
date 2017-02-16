import java.nio.ByteBuffer;

public class Measurement {
	
	public static final int BYTESIZE = 12;
	private int id;
	private Double value;
	
	public Measurement(byte[] argMeasurementBytes) {
		ByteBuffer buff = ByteBuffer.wrap(argMeasurementBytes);
		id = buff.getInt();
		value = buff.getDouble();
	}
	
	public int getId() {
		return id;
	}
	
	public Double getValue() {
		// avoid giving an object reference
		return value.doubleValue();
	}
	
	public void setValue(Double argValue) {
		value = argValue;
	}
	
	public MeasurementId getMeasurementId() {
		return MeasurementId.getId(id);
	}
	
	public byte[] toByteArray() {
		ByteBuffer buff = ByteBuffer.allocate(BYTESIZE);
		buff.putInt(id);
		buff.putDouble(value);
		return buff.array();
	}
	
	@Override
	public String toString() {
		return getMeasurementId().getName() + ": " + value;
	}
	
}
