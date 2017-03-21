
public class SystemB {

	   public static void main( String argv[])
	   {
		   String inputfile, outputfile, wildpointfile;
		   if(argv.length<3){
			   inputfile = "FlightData.dat";
			   outputfile = "OutputB.dat";
			   wildpointfile = "Wildpoints.dat";
			   
		   }
		   else{
			   inputfile = argv[0];
			   outputfile = argv[1];
			   wildpointfile = argv[2];
		   }
	    SourceFilterFileReader sourceFilter = new SourceFilterFileReader(inputfile);
			FahrenheitToCelsiusFilter fahrenheitToCelsiusFilter = new FahrenheitToCelsiusFilter();
			PressureFilter pressureFilter = new PressureFilter();
			FeetToMeterFilter feetToMeterFilter = new FeetToMeterFilter();
			DataSelectionFilter selectFilter = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.ALTITUDE, MeasurementId.TEMPERATURE, MeasurementId.PRESSURE);
			SinkFilterFileWriter sinkFilter = new SinkFilterFileWriter(outputfile);
			SinkFilterFileWriter wildpointSink = new SinkFilterFileWriter(wildpointfile);
			DataSelectionFilter wildSelect = new DataSelectionFilter(MeasurementId.TIME,MeasurementId.PRESSURE_WILD);
			sinkFilter.Connect(selectFilter); // Connect sinkFilter input port to selecFilter output port.
			selectFilter.Connect(feetToMeterFilter);
			feetToMeterFilter.Connect(pressureFilter); 
			pressureFilter.Connect(fahrenheitToCelsiusFilter);
			wildSelect.Connect(pressureFilter);
			wildpointSink.Connect(wildSelect);
			fahrenheitToCelsiusFilter.Connect(sourceFilter); 

			sourceFilter.start();
			fahrenheitToCelsiusFilter.start();
			pressureFilter.start();
			feetToMeterFilter.start();
			selectFilter.start();
			sinkFilter.start();
			wildSelect.start();
			wildpointSink.start();
	   }
}
