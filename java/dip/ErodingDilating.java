package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class ErodingDilating {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.IMREAD_COLOR);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        int erosion_size = 5;
        Mat element = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                new Size(2 * erosion_size + 1, 2 * erosion_size + 1));
        Imgproc.erode(source, destination, element);
        Imgcodecs.imwrite("erosion.jpg", destination);

        int dilation_size = 5;
        Mat element1 = Imgproc.getStructuringElement(Imgproc.MORPH_RECT,
                new Size(2 * dilation_size + 1, 2 * dilation_size + 1));
        Imgproc.dilate(source, destination, element1);
        Imgcodecs.imwrite("dilation.jpg", destination);

    }
}
