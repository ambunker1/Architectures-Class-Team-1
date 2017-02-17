
public class SystemB {

	   public static void main( String argv[])
	   {
	    SourceFilterFileReader sourceFilter = new SourceFilterFileReader("DataSets/FlightData.dat");
			FahrenheitToCelsiusFilter fahrenheitToCelsiusFilter = new FahrenheitToCelsiusFilter();
			PressureFilter pressureFilter = new PressureFilter();
			FeetToMeterFilter feetToMeterFilter = new FeetToMeterFilter();
			DataSelectionFilter selectFilter = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.ALTITUDE, MeasurementId.TEMPERATURE, MeasurementId.PRESSURE);
			SinkFilterFileWriter sinkFilter = new SinkFilterFileWriter("OutputB.dat");
			sinkFilter.Connect(selectFilter); // Connect sinkFilter input port to selecFilter output port.
			selectFilter.Connect(feetToMeterFilter);
			feetToMeterFilter.Connect(pressureFilter); 
			pressureFilter.Connect(fahrenheitToCelsiusFilter);
			fahrenheitToCelsiusFilter.Connect(sourceFilter); 

			sourceFilter.start();
			fahrenheitToCelsiusFilter.start();
			pressureFilter.start();
			feetToMeterFilter.start();
			selectFilter.start();
			sinkFilter.start();
	   }
}
