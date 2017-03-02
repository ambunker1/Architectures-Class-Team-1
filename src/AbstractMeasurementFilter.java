/**
 * An abstract filter implementation that makes concrete filter implementations
 * easier and concise. Concrete filters will only need to implement the
 * byte[] doFilter(Measurement argMeasurement) method.
 * @author team-one
 */
public abstract class AbstractMeasurementFilter extends FilterFramework{
  public void run() {
    int bytesread = 0;    // Number of bytes read from the input file.
    int byteswritten = 0; // Number of bytes written to the stream.
    byte databyte = 0;    // The byte of data read from the file

    // Next we write a message to the terminal to let the world know we are
    // alive...

    MeasurementReader measurementReader = new MeasurementReader();



    while (true) {
      /*************************************************************
       * Here we read a byte and write a byte
       *************************************************************/

      try {
        databyte = ReadFilterInputPort();
        bytesread++;
        measurementReader.put(databyte);

        if (measurementReader.hasMeasurementAvailable()) {
            byteswritten += readAndFilter(measurementReader);
        }

      } // try

      catch (EndOfStreamException e) {
        ClosePorts();

        break;

      } // catch

    } // while

  } // run
  
  /**
   * Reads measurement data from the supplied reader and filters the data. The
   * filtered data then gets written to the output port.
   * @param argReader measurement reader
   * @return byte count of the data from the filter
   */
  protected int readAndFilter(MeasurementReader argReader) {
    int bytesFromFilter = 0;
    Measurement m = argReader.pollMeasurement();
    byte[] filteredData = doFilter(m);
    if (filteredData != null) {
      WriteFilterOutputPort(m.toByteArray());
      bytesFromFilter = m.toByteArray().length;
    }
    return bytesFromFilter;
  }
  
  /**
   * The main filter logic is defined in here.
   * @param argMeasurement measurement to process
   * @return filtered data
   */
  protected abstract byte[] doFilter(Measurement argMeasurement);

}
