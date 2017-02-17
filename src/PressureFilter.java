

/**
 * A filter that converts 
 * @author team-one
 */
public class PressureFilter extends FilterFramework {
	
  private FrameReader frameReader = new FrameReader();
  private Frame wildFrame = null;
  boolean needWildCorrection = false;
  private Measurement lastPressure;
  boolean firstPressure = true;
  
  public void run()
  {


		int bytesread = 0;					// Number of bytes read from the input file.
		int byteswritten = 0;				// Number of bytes written to the stream.
		byte databyte = 0;					// The byte of data read from the file



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
				frameReader.put(databyte);
				Frame frame ;
				if(frameReader.hasFrameAvailable()){
					frame = frameReader.pollFrame();
				}
				else{
					continue;
				}
				if(needWildCorrection){
					double correctedPressure;
					if(firstPressure){
						correctedPressure = frame.getMeasurement(MeasurementId.PRESSURE).getValue();
					}
					else{
						correctedPressure = (lastPressure.getValue() + frame.getMeasurement(MeasurementId.PRESSURE).getValue()) /2.0;
					}
					wildFrame.setMeasurement(MeasurementId.PRESSURE, correctedPressure);
					for(byte b : wildFrame.toByteArray()){
						WriteFilterOutputPort(b);
					}
					needWildCorrection = false;
				}
				if(frame.hasMeasurement(MeasurementId.PRESSURE)){
					Measurement pressure = frame.getMeasurement(MeasurementId.PRESSURE);
					if(pressure.getValue()<0 || (!firstPressure && Math.abs(pressure.getValue() - lastPressure.getValue())>10)){
						wildFrame = frame;
						needWildCorrection = true;
					}
					else{
						lastPressure = pressure;
						firstPressure = false;
					}
				}
				byteswritten++;
				if(!needWildCorrection){
					for(byte b : frame.toByteArray()){
						WriteFilterOutputPort(b);
					}
				}

			} // try

			catch (EndOfStreamException e)
			{
				ClosePorts();
				System.out.print( "\n" + this.getName() + "::Middle Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten );
				break;

			} // catch

		} // while

 } // run



}
