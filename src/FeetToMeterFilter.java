
public class FeetToMeterFilter extends FilterFramework {

	public void run()
    {
		int bytesread = 0;					// Number of bytes read from the input file.
		int byteswritten = 0;				// Number of bytes written to the stream.
		byte databyte = 0;					// The byte of data read from the file

		// Next we write a message to the terminal to let the world know we are alive...
		
		FrameReader frameReader = new FrameReader();

		System.out.print( "\n" + this.getName() + "::Middle Reading ");

		while (true)
		{
			/*************************************************************
			*	Here we read a byte and write a byte
			*************************************************************/

			try
			{
				databyte = ReadFilterInputPort();
				bytesread++;
				frameReader.addByte(databyte);
				
				if (frameReader.hasFrameAvailable()) {
					Frame frame = frameReader.getFrame();
					convertFeetToMeter(frame);
					WriteFilterOutputPort(frame.toByteArray());
					byteswritten += frame.toByteArray().length;
				}
				

			} // try

			catch (EndOfStreamException e)
			{
				frameReader.stop();
				Frame frame = frameReader.getFrame();
				convertFeetToMeter(frame);
				WriteFilterOutputPort(frame.toByteArray());
				byteswritten += frame.toByteArray().length;

				ClosePorts();
				System.out.print( "\n" + this.getName() + "::Middle Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
				break;

			} // catch

		} // while

   } // run
	
	private void convertFeetToMeter(Frame argFrame) {
		for (Measurement m : argFrame.getMeasurements()) {
			if (m.getMeasurementId() == MeasurementId.ALTITUDE) {
				// convert fahrenheit to celsius
				m.setValue(m.getValue() / 0.3048);
			}
		}
	}

}
