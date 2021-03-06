
public class MergeFilter extends FilterFramework {

    private FilterFramework Input1 = new FilterFramework();
    private FilterFramework Input2 = new FilterFramework();

    boolean input1Done = false;
    boolean input2Done = false;

    void Connect(FilterFramework upstreamFilter, int index) {
        if (index == 1) {
            Input1.Connect(upstreamFilter);
        } else if (index == 2) {
            Input2.Connect(upstreamFilter);
        }
    }

    public void run() {
        byte databyte = 0;    // The byte of data read from the file

        // Next we write a message to the terminal to let the world know we are
        // alive...
        FrameReader frameReader1 = new FrameReader();
        FrameReader frameReader2 = new FrameReader();

        while (true) {
            /**
             * ***********************************************************
             * Here we read a byte and write a byte
		       ************************************************************
             */
            if (input1Done && input2Done) {
                break;
            }
            if (!input1Done) {
                try {
                    databyte = Input1.ReadFilterInputPort();
                    frameReader1.put(databyte);
                    if (frameReader1.hasFrameAvailable()) {
                        WriteFilterOutputPort(frameReader1.pollFrame().toByteArray());
                    }

                } // try
                catch (EndOfStreamException e) {
                    Input1.ClosePorts();
                    input1Done = true;
                }
            }
            if (!input2Done) {
                try {
                    databyte = Input2.ReadFilterInputPort();
                    frameReader2.put(databyte);
                    if (frameReader2.hasFrameAvailable()) {
                        WriteFilterOutputPort(frameReader2.pollFrame().toByteArray());
                    }
                } catch (EndOfStreamException e) {
                    Input2.ClosePorts();
                    input2Done = true;
                }
            }

        } // while

    } // run

}
