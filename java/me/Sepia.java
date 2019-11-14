package me;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.scijava.nativelib.NativeLoader;

/**
 * Using a kernel to get sepia picture
 */
public class Sepia {

    public static void main(String[] args) throws Exception {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String filename = "marcel.jpg";
        // String filename = args[0];
        Mat source = Imgcodecs.imread(filename);

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
        Core.transform(source, destination, kernel);

        Imgcodecs.imwrite("target/sepia2_" + new File(filename).getName(), destination);
    }
}