package me.filters;

import java.io.File;
import java.util.concurrent.Callable;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import picocli.CommandLine;

import static org.opencv.core.Core.*;
import static org.opencv.imgcodecs.Imgcodecs.*;
import static org.scijava.nativelib.NativeLoader.*;

/**
 * Using a kernel to get sepia picture
 */
@CommandLine.Command(name = "sepia", version="1.0.0", mixinStandardHelpOptions = true, description = "Turn a picture into sepia")
public class Sepia implements Callable<Integer>,Filter {

    @CommandLine.Option(names = {"-i", "--input"}, description = "Input Image", required = true)
    private String filename = "marcel.jpg";

    @CommandLine.Option(names = {"-o", "--output"}, description = "Output Folder", required = false)
    private File output = new File("out");

    public Mat apply(Mat source) {
        // mat is in BGR
        Mat kernel = new Mat(3, 3, CvType.CV_32F);
        kernel.put(0, 0,
                // green
                0.272, 0.534, 0.131,
                // blue
                0.349, 0.686, 0.168,
                // red
                0.393, 0.769, 0.189);
        Mat destination = new Mat();
        transform(source, destination, kernel);
        return destination;
    }

    public Integer call() {
        Mat source = imread(filename);
        Mat destination = apply(source);
        output.mkdirs();
        imwrite(output.getAbsolutePath()+"/sepia_" + new File(filename).getName(), destination);
        return 0;
    }

    public static void main(String... args) throws Exception {
        loadLibrary(NATIVE_LIBRARY_NAME);
        new CommandLine(new Sepia()).execute(args);
    }
}