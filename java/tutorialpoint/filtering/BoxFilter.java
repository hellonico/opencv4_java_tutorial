package tutorialpoint.filtering;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static origami.Origami.init;

public class BoxFilter {
    public static void main(String... args) {
        init();
        Mat src = imread("data/marcel2019.jpg");
        Mat dst = new Mat();
        Size size = new Size(45, 45);
        Point point = new Point(-1, -1);
        Imgproc.boxFilter(src, dst, 50, size, point, true, Core.BORDER_DEFAULT);
        imwrite("boxFilter.jpg",dst);
    }
}
