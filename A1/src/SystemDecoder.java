/**
 * A data file decoder. <br/>
 * Decodes the byte stream of data to a human readable text.
 * 
 * @author team-one
 */
public class SystemDecoder {

  public static void main(String[] args) {
    SourceFilterFileReader sourceFilter = new SourceFilterFileReader("DataSets/SubSetA.dat");
    SinkFilterFileWriter sinkFilter = new SinkFilterFileWriter("SubSetA.txt");

    sinkFilter.Connect(sourceFilter);

    sourceFilter.start();
    sinkFilter.start();
  }

}
