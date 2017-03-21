
public class SystemC {

    public static void main(String argv[]) {
    	String infile1, infile2, outfile, wildout, lessthanout;
    	if(argv.length<5){
    		infile1 = "SubSetA.dat";
    		infile2 = "SubSetB.dat";
    		outfile = "OutputC.dat";
    		wildout = "PressureWildpoints.dat";
    		lessthanout = "LessThan10k.dat";
    	}
    	else{
    		infile1 = argv[0];
    		infile2 = argv[1];
    		outfile = argv[2];
    		wildout = argv[3];
    		lessthanout = argv[4];
    	}
        SourceFilterFileReader sourceFilter1 = new SourceFilterFileReader(infile1);
        SourceFilterFileReader sourceFilter2 = new SourceFilterFileReader(infile2);
                                                                    
        DataSelectionFilter selectFilter1 = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.VELOCITY, MeasurementId.ALTITUDE, MeasurementId.PRESSURE, MeasurementId.TEMPERATURE, MeasurementId.ATTITUDE);
        DataSelectionFilter selectFilter2 = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.VELOCITY, MeasurementId.ALTITUDE, MeasurementId.PRESSURE, MeasurementId.TEMPERATURE, MeasurementId.ATTITUDE);
        MergeFilter mergeFilter = new MergeFilter();
        DataSortFilter sortFilter = new DataSortFilter();
        
        FahrenheitToCelsiusFilter fahrenheitToCelsiusFilter = new FahrenheitToCelsiusFilter();
        PressureFilter pressureFilter = new PressureFilter();
        FeetToMeterFilter feetToMeterFilter = new FeetToMeterFilter();
        SinkFilterFileWriter sinkFilter = new SinkFilterFileWriter(outfile);
        
        DataSelectionFilter wildpointSelect = new DataSelectionFilter(MeasurementId.TIME, MeasurementId.PRESSURE_WILD);
        SinkFilterFileWriter wildpointSink = new SinkFilterFileWriter(wildout); 
        
        Under10KFilterWriter tenkfilter = new Under10KFilterWriter();
        SinkFilterFileWriter tenkWriter = new SinkFilterFileWriter(lessthanout);
        
        selectFilter1.Connect(sourceFilter1);
        selectFilter2.Connect(sourceFilter2);
        mergeFilter.Connect(selectFilter1, 1);
        mergeFilter.Connect(selectFilter2, 2);
        
        //create sort filter and put sort between merge and rest of stream
        sortFilter.Connect(mergeFilter);
        
        fahrenheitToCelsiusFilter.Connect(sortFilter);
        pressureFilter.Connect(fahrenheitToCelsiusFilter);
        wildpointSelect.Connect(pressureFilter);
        wildpointSink.Connect(wildpointSelect);
        feetToMeterFilter.Connect(pressureFilter);
        sinkFilter.Connect(feetToMeterFilter);
        
        tenkfilter.Connect(feetToMeterFilter);
        tenkWriter.Connect(tenkfilter);
                
        sourceFilter1.start();
        sourceFilter2.start();
        selectFilter1.start();
        selectFilter2.start();
        mergeFilter.start();
        sortFilter.start();
        fahrenheitToCelsiusFilter.start();
        pressureFilter.start();
        feetToMeterFilter.start();
        sinkFilter.start();
        tenkfilter.start();
        tenkWriter.start();
        wildpointSelect.start();
        wildpointSink.start();
    }
}
