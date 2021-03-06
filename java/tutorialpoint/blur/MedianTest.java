package tutorialpoint.blur;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static origami.Origami.init;

public class MedianTest {
    public static void main(String... args) {
        init();
        Mat src = imread("data/marcel2019.jpg");
        Mat dst = new Mat();
        Imgproc.medianBlur(src, dst, 15);
        Imgcodecs.imwrite("blur.jpg", dst);
    }
}
