package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class EnhanceImageSharpness {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.IMREAD_COLOR);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.GaussianBlur(source, destination, new Size(1, 1), 10);
        Core.addWeighted(source, 1.5, destination, -0.5, 0, destination);
        Imgcodecs.imwrite("sharp.jpg", destination);
    }
}
