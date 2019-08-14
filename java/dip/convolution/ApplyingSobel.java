package dip.convolution;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class ApplyingSobel {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        int kernelSize = 9;

        Mat source = Imgcodecs.imread("data/dip/grayscale.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Mat kernel = new Mat(kernelSize, kernelSize, CvType.CV_32F) {
            {
                put(0, 0, -1);
                put(0, 1, 0);
                put(0, 2, 1);

                put(1, 0 - 2);
                put(1, 1, 0);
                put(1, 2, 2);

                put(2, 0, -1);
                put(2, 1, 0);
                put(2, 2, 1);
            }
        };

        Imgproc.filter2D(source, destination, -1, kernel);
        Imgcodecs.imwrite("sobel.jpg", destination);

    }
}
