
public class SystemA {

  public static void main(String argv[]) {
	String inputfile, outputfile;
	if(argv.length<2){
		inputfile="FlightData.dat";
		outputfile = "OutputA.dat";
	}
	else{
		inputfile = argv[0];
		outputfile = argv[1];
	}
    SourceFilterFileReader sourceFilter = new SourceFilterFileReader(inputfile);
    FahrenheitToCelsiusFilter fahrenheitToCelsiusFilter = new FahrenheitToCelsiusFilter();
    FeetToMeterFilter feetToMeterFilter = new FeetToMeterFilter();
    DataSelectionFilter selectFilter = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.ALTITUDE,
        MeasurementId.TEMPERATURE);
    SinkFilterFileWriter sinkFilter = new SinkFilterFileWriter(outputfile);
    sinkFilter.Connect(selectFilter); // Connect sinkFilter input port to
                                      // selecFilter output port.
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
