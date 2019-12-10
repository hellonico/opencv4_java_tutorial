package tutorialpoint.blur;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static origami.Origami.init;

public class BlurTest {

    public static void main(String... args) {
        init();
        Mat src = imread("data/marcel2019.jpg");
        Mat dst = new Mat();
        Size size = new Size(100, 100);
        Point point = new Point(20, 30);
        Imgproc.blur(src, dst, size, point, Core.BORDER_REFLECT);
        Imgcodecs.imwrite("blur.jpg", dst);
    }
}
