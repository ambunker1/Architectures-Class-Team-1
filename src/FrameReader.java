import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility for various byte conversion and creating structures from steam of bytes. 
 * @author team one
 */
public class FrameReader {
	
	public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy:MM:dd:HH:mm:SS");
	private ByteBuffer buffer = ByteBuffer.allocate(12);
	private List<Frame> frames = new ArrayList<Frame>();
	
	private Frame currentFrame = null;
	private boolean inReadMode = true;
	
	public void addByte(byte argByte) {
		buffer.put(argByte);
		if (! buffer.hasRemaining()) {
			Measurement m = new Measurement(buffer.array());
			buffer.clear();
			
			if (m.getMeasurementId().equals(MeasurementId.TIME)) {
				// first frame
				if (currentFrame == null) {
					currentFrame = new Frame();
				}
				// next frame came
				else {
					// move current frame, then create a new frame for the next in line
					frames.add(currentFrame);
					currentFrame = new Frame();
				}
				// old or new, we need to add a reference
				frames.add(currentFrame);
			}
			currentFrame.addMeasurement(m);
		}
	}
	
	public boolean hasFrameAvailable() {
		if (inReadMode) {
			return frames.size() > 1;
		}
		else {
			return frames.size() > 0;
		}
	}
	
	public Frame getFrame() {
		if (inReadMode) {
			if (frames.size() > 1) {
				return frames.remove(0);
			}
			else {
				return null;
			}
		}
		else {
			if (frames.size() != 0) {
				return frames.remove(0);
			}
			else {
				return null;
			}
		}
	}
	
	public void stop() {
		inReadMode = false;
	}
}
