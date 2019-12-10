package tutorialpoint.blur;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static origami.Origami.init;

public class GaussianTest {
    public static void main(String... args) {
        init();
        Mat src = imread("data/marcel2019.jpg");
        Mat dst = new Mat();
        Imgproc.GaussianBlur(src, dst, new Size(45, 45), 0);
        Imgcodecs.imwrite("blur.jpg", dst);
    }
}
