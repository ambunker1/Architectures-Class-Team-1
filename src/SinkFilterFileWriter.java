import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes frames of measurement data to a given file.
 * @author team-one
 */
public class SinkFilterFileWriter extends FilterFramework {
  
  private File outFile;
  private static final String NEWLINE = "\n";
  
  public SinkFilterFileWriter(String argFilename) {
    outFile = new File(argFilename); 
  }
  
  public void run() {
    try {
      int bytesread = 0;    // Number of bytes read from the input file.
      int byteswritten = 0; // Number of bytes written to the stream.
      byte databyte = 0;    // The byte of data read from the file
  
      // Next we write a message to the terminal to let the world know we are
      // alive...
  
      FrameReader frameReader = new FrameReader();
      PrintWriter buffWriter = new PrintWriter(outFile);
  
      while (true) {
        /*************************************************************
         * Here we read a byte and write a byte
         *************************************************************/
  
        try {
          databyte = ReadFilterInputPort();
          bytesread++;
          frameReader.put(databyte);
  
          if (frameReader.hasFrameAvailable()) {

          Frame f = frameReader.pollFrame();
            buffWriter.write(f.toString());
            buffWriter.write(NEWLINE);
          }
  
        } // try
  
        catch (EndOfStreamException e) {
          frameReader.stop();
          while (frameReader.hasFrameAvailable()) {
          Frame f = frameReader.pollFrame();
            buffWriter.write(f.toString());  
          }
          ClosePorts();
          System.out.print(
              "\n" + this.getName() + "::Middle Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten);
          break;
  
        } // catch
  
      } // while
      buffWriter.flush();
      buffWriter.close();
      
    } catch (IOException e1) {
      e1.printStackTrace();
    }

  } // run

}
