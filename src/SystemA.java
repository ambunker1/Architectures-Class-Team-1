
public class SystemA {

	   public static void main( String argv[])
	   {
			SourceFilter sourceFilter = new SourceFilter();
			FahrenheitToCelsiusFilter fahrenheitToCelsiusFilter = new FahrenheitToCelsiusFilter();
			FeetToMeterFilter feetToMeterFilter = new FeetToMeterFilter();
			DataSelectionFilter selectFilter = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.ALTITUDE, MeasurementId.TEMPERATURE);
			SinkFilterFileWriter sinkFilter = new SinkFilterFileWriter("OutputA.dat");

			sinkFilter.Connect(selectFilter); // Connect sinkFilter input port to selecFilter output port.
			selectFilter.Connect(feetToMeterFilter);
			feetToMeterFilter.Connect(fahrenheitToCelsiusFilter); 
			fahrenheitToCelsiusFilter.Connect(sourceFilter); 

			sourceFilter.start();
			fahrenheitToCelsiusFilter.start();
			feetToMeterFilter.start();
			selectFilter.start();
			sinkFilter.start();
	   }
}
