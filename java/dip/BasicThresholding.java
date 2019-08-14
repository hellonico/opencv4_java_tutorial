package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class BasicThresholding {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.IMREAD_COLOR);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Imgproc.threshold(source, destination, 127, 255, Imgproc.THRESH_TOZERO);
        Imgcodecs.imwrite("ThreshZero.jpg", destination);

        Imgproc.threshold(source, destination, 127, 255, Imgproc.THRESH_TOZERO_INV);
        Imgcodecs.imwrite("ThreshZeroInv.jpg", destination);

        Imgproc.threshold(source, destination, 127, 255, Imgproc.THRESH_BINARY);
        Imgcodecs.imwrite("ThreshBinary.jpg", destination);

        Imgproc.threshold(source, destination, 127, 255, Imgproc.THRESH_BINARY_INV);
        Imgcodecs.imwrite("ThreshBinaryInv.jpg", destination);

    }
}
