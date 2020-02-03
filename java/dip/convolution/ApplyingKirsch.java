package dip.convolution;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.io.IOException;

public class ApplyingKirsch {
    public static void main(String[] args) throws IOException {
        Origami.init();
        int kernelSize = 9;

        Mat source = Imgcodecs.imread("data/dip/grayscale.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Mat kernel = new Mat(kernelSize, kernelSize, CvType.CV_32F) {
            {
                put(0, 0, -3);
                put(0, 1, -3);
                put(0, 2, -3);

                put(1, 0 - 3);
                put(1, 1, 0);
                put(1, 2, -3);

                put(2, 0, 5);
                put(2, 1, 5);
                put(2, 2, 5);
            }
        };

        Imgproc.filter2D(source, destination, -1, kernel);
        HighGui.imshow("hello", destination);
        HighGui.waitKey();
        // Imgcodecs.imwrite("kirsch.jpg", destination);

    }
}
