package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class GaussianFilter {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg",
                Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Mat destination = new Mat(source.rows(),source.cols(),source.type());

        Imgproc.GaussianBlur(source, destination,new Size(11,11), 0);
        Imgcodecs.imwrite("gaussianblur1.jpg", destination);

        Imgproc.GaussianBlur(source, destination,new Size(45,45), 0);
        Imgcodecs.imwrite("gaussianblur45.jpg", destination);

    }
}
