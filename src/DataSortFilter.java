/**
 * A filter that sorts all data frames in ascending order by time.
 *
 * @author team-one
 */
public class DataSortFilter extends FilterFramework {

    private FrameReader frameReader = new FrameReader();
    private Frame[] sortedDataArray;

    public void run() {

        int bytesread = 0;					// Number of bytes read from the input file.
        int byteswritten = 0;				// Number of bytes written to the stream.
        byte databyte = 0;					// The byte of data read from the file

        System.out.print("\n" + this.getName() + "::Middle Reading ");
        while (true) {
            /**
             * ***********************************************************
             * Here we read a byte and write a byte
             * ***********************************************************
             */

            try {
                databyte = ReadFilterInputPort();
                bytesread++;
                frameReader.put(databyte);
            } // try
            catch (EndOfStreamException e) {
                frameReader.stop();
                System.out.print("\n" + this.getName() + "::Middle Exiting; bytes read: " + bytesread + " bytes written: " + byteswritten);
                break;

            } // catch

        } // while

        //sorting frames (insertion sort)
        sortedDataArray = new Frame[frameReader.getFramesSize()];
        for (int r = 0; r < sortedDataArray.length; r++) {
            sortedDataArray[r] = frameReader.pollFrame();
        }

        Frame key;
        int j;
        for (j = 1; j < sortedDataArray.length; j++) {
            key = sortedDataArray[j];
            int i = j - 1;
            while ((i > -1) && (sortedDataArray[i].getMeasurement(MeasurementId.TIME).getValue() > key.getMeasurement(MeasurementId.TIME).getValue())) {
                sortedDataArray[i + 1] = sortedDataArray[i];
                i--;
            }
            sortedDataArray[i + 1] = key;
        }

        //writing output.
        for (Frame frame : sortedDataArray) {
            byteswritten++;
            for (byte b : frame.toByteArray()) {
                WriteFilterOutputPort(b);
            }
        }
        ClosePorts();
    } // run

}
