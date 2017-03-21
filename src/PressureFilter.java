
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



		byte databyte = 0;					// The byte of data read from the file
		Frame finalFrame = null;
		



		while (true)
		{
			/*************************************************************
			*	Here we read a byte and write a byte
			*************************************************************/

			try
			{
				databyte = ReadFilterInputPort();

				frameReader.put(databyte);
				Frame frame ;
				if(frameReader.hasFrameAvailable()){
					frame = frameReader.pollFrame();
				}
				else{
					continue;
				}
				finalFrame = frame;
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

				if(!needWildCorrection){
					for(byte b : frame.toByteArray()){
						WriteFilterOutputPort(b);
					}
				}
				else{
					Measurement wild = wildFrame.getMeasurement(MeasurementId.PRESSURE);
					wild.setId(MeasurementId.PRESSURE_WILD.ordinal());
					Frame outFrame = new Frame();
					outFrame.addMeasurement(wild);
					for(byte b : outFrame.toByteArray()){
						WriteFilterOutputPort(b);
					}
				}

			} // try

			catch (EndOfStreamException e)
			{
				for(byte b : finalFrame.toByteArray()){
					WriteFilterOutputPort(b);
				}
				ClosePorts();
				break;

			} // catch

		} // while

 } // run



}
