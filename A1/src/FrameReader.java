import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Queue;

/**
 * A utility that reads bytes one at a time and builds frames out of the byte
 * sequence. Holds the frames until they get polled out. <br/><br/> 
 * A frame always starts with a Time measurement. <br/><br/>
 * This class maybe useful for implementing the System-C.
 * 
 * @author team-one
 */
public class FrameReader {

  public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");

  // LinkedList is O(1) for Queue.add, Queue.poll, and Queue.peek operations.
  private Queue<Frame> frames = new LinkedList<Frame>();

  private Frame currentFrame = null;
  private boolean inReadMode = true;
  private MeasurementReader mreader = new MeasurementReader();

  /**
   * Feed the reader a byte.
   * @param argByte byte to feed
   */
  public void put(byte argByte) {
    mreader.put(argByte);
    if (mreader.hasMeasurementAvailable()) {
      Measurement m = mreader.pollMeasurement();
      if (m.getMeasurementId().equals(MeasurementId.TIME)) {
        // first frame
        if (currentFrame == null) {
          currentFrame = new Frame();
        }
        // next frame is ready for creation
        else {
          // the frame is full, move it out of the way
          // create a new frame, set it current
          currentFrame = new Frame();
        }
        frames.add(currentFrame);
      }
      currentFrame.addMeasurement(m);
    }
  }

  /**
   * Checks if a full frame is available or not.
   * @return true if a full frame is available, false otherwise
   */
  public boolean hasFrameAvailable() {
    if (inReadMode) {
      return frames.size() > 1;
    } else {
      return frames.size() > 0;
    }
  }

  /**
   * Polls the first frame.
   * @return the first frame
   */
  public Frame pollFrame() {
    if (inReadMode) {
      return frames.size() > 1 ? frames.poll() : null;
    } else {
      return frames.size() != 0 ? frames.poll() : null;
    }
  }
  

  /**
   * Tell the reader that it will not receive any byte from now on. Invoke
   * this function to flush out the last frame.
   */
  public void stop() {
    inReadMode = false;
  }
}
