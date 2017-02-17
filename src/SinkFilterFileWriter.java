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
     // FileWriter fileWriter = new FileWriter(outFile);
     // BufferedWriter buffWriter = new BufferedWriter(fileWriter);
      PrintWriter buffWriter = new PrintWriter(outFile);
      int framesWritten = 0;
  
 
  
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
//        	System.out.println("Writing Frame "+ framesWritten + " : " + f.toString());
            buffWriter.write(f.toString());
            buffWriter.write(NEWLINE);
            framesWritten++;
 //           System.out.println(bytesread + " bytes read and " + framesWritten + " frames written");
          }
  
        } // try
  
        catch (EndOfStreamException e) {
          frameReader.stop();
          while (frameReader.hasFrameAvailable()) {
        	Frame f = frameReader.pollFrame();
     //   	System.out.println("Writing Frame: " + f.toString());
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
     // fileWriter.flush();
     // fileWriter.close();
      
    } catch (IOException e1) {
      e1.printStackTrace();
    }

  } // run

}
