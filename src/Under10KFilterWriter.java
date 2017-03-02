

public class Under10KFilterWriter extends FilterFramework{
	private FrameReader frameReader = new FrameReader();
	byte databyte;
	public void run(){
		while (true) {
		      /*************************************************************
		       * Here we read a byte and write a byte
		       *************************************************************/

		      try {
		        databyte = ReadFilterInputPort();

		        frameReader.put(databyte);

		        if (frameReader.hasFrameAvailable()) {
		            Frame frame = frameReader.pollFrame();
		            if(frame.getMeasurement(MeasurementId.ALTITUDE).getValue()<10000){
		            	for(byte b : frame.toByteArray()){
		            		WriteFilterOutputPort(b);
		            	}
		            }
		        }

		      } // try

		      catch (EndOfStreamException e) {
		        ClosePorts();

		        break;

		      } // catch
	}
	}

}
