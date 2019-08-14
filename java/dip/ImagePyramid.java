package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class ImagePyramid {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.IMREAD_COLOR);

        Mat destination1 = new Mat(source.rows() * 2, source.cols() * 2, source.type());

        Imgproc.pyrUp(source, destination1, new Size(source.cols() * 2, source.rows() * 2));
        Imgcodecs.imwrite("pyrUp.jpg", destination1);

        source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.IMREAD_COLOR);

        Mat destination = new Mat(source.rows() / 2, source.cols() / 2, source.type());
        Imgproc.pyrDown(source, destination, new Size(source.cols() / 2, source.rows() / 2));
        Imgcodecs.imwrite("pyrDown.jpg", destination);

    }
}
