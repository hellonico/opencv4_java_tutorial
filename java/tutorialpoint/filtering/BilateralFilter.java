package tutorialpoint.filtering;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static origami.Origami.init;

public class BilateralFilter {
    public static void main(String... args) {
        init();
        Mat src = imread("data/marcel2019.jpg");
        Mat dst = new Mat();
        Imgproc.bilateralFilter(src, dst, 15, 80, 80, Core.BORDER_DEFAULT);
        imwrite("bilateral.jpg",dst);
    }
}
