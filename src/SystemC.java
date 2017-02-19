
public class SystemC {

	   public static void main( String argv[])
	   {
		   SourceFilterFileReader sourceFilter1 = new SourceFilterFileReader("DataSets/SubSetA.dat");
		   SourceFilterFileReader sourceFilter2 = new SourceFilterFileReader("DataSets/SubSetB.dat");
		   DataSelectionFilter selectFilter1 = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.ALTITUDE, MeasurementId.TEMPERATURE, MeasurementId.PRESSURE);
		   DataSelectionFilter selectFilter2 = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.ALTITUDE, MeasurementId.TEMPERATURE, MeasurementId.PRESSURE);
		   MergeFilter mergeFilter = new MergeFilter();
		   selectFilter1.Connect(sourceFilter1);
		   selectFilter2.Connect(sourceFilter2);
		   mergeFilter.Connect(selectFilter1, 1);
		   mergeFilter.Connect(selectFilter2);
		   //create sort filter and put sort between merge and rest of stream
		   
			FahrenheitToCelsiusFilter fahrenheitToCelsiusFilter = new FahrenheitToCelsiusFilter();
			PressureFilter pressureFilter = new PressureFilter();
			FeetToMeterFilter feetToMeterFilter = new FeetToMeterFilter();
			DataSelectionFilter selectFilter = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.ALTITUDE, MeasurementId.TEMPERATURE, MeasurementId.PRESSURE);
			SinkFilterFileWriter sinkFilter = new SinkFilterFileWriter("OutputB.dat");
			
			fahrenheitToCelsiusFilter.Connect(mergeFilter); 
			pressureFilter.Connect(fahrenheitToCelsiusFilter);
			feetToMeterFilter.Connect(pressureFilter); 



			sourceFilter1.start();
			sourceFilter2.start();
			selectFilter1.start();
			selectFilter2.start();
			mergeFilter.start();
			fahrenheitToCelsiusFilter.start();
			pressureFilter.start();
			feetToMeterFilter.start();
			selectFilter.start();
			sinkFilter.start();
	   }
}
