package tutorialpoint.filtering;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static origami.Origami.init;

public class SQRBoxFilterTest {
    public static void main(String... args) {
        init();
        Mat src = imread("data/marcel2019.jpg");
        Mat dst = new Mat();
        Imgproc.sqrBoxFilter(src, dst, -1, new Size(1, 1));
        Imgproc.applyColorMap(src, dst, Imgproc.COLORMAP_HOT);
        imwrite("sqrBoxFilter.jpg",dst);
    }
}
