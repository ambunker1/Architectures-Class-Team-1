
public class SystemA {

	   public static void main( String argv[])
	   {
			SourceFilter filter1 = new SourceFilter();
			FahrenheitToCelsiusFilter filter2 = new FahrenheitToCelsiusFilter();
			FeetToMeterFilter filter3 = new FeetToMeterFilter();
			SinkFilter filter4 = new SinkFilter();
			MeasurementId[] ids = new MeasurementId[2];
			ids[0] = MeasurementId.ALTITUDE;
			ids[1] = MeasurementId.TEMPERATURE;
			
			DataSelectionFilter selectFilter = new DataSelectionFilter(ids);

			filter4.Connect(filter3);
			filter3.Connect(filter2); // This essentially says, "connect Filter3 input port to Filter2 output port
			filter2.Connect(filter1); // This essentially says, "connect Filter2 intput port to Filter1 output port

			filter1.start();
			filter2.start();
			filter3.start();
			filter4.start();
	   }
}
