package stackoverflow;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class OptimizingGrabcut {
    /**
     * https://codeday.me/jp/qa/20190210/234255.html
     * has copied something from
     * https://stackoverflow.com/questions/30872353/optimizing-performance-of-grabcut-in-opencv-java
     */
    public static void main(String... args) {
        Origami.init();
        Mat mat = imread("data/marcel2019.jpg");
        Mat result = extractFace(mat, 300, 1200, 300, 900);
        imwrite("output.jpg", result);

    }

    public static Mat extractFace(Mat image, int xOne, int xTwo, int yOne, int yTwo) {

        Rect rectangle = new Rect(xOne, yOne, xTwo, yTwo);
        Mat result = new Mat();
        Mat bgdModel = new Mat();
        Mat fgdModel = new Mat();
        Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(3));
        Imgproc.grabCut(image, result, rectangle, bgdModel, fgdModel, 8, Imgproc.GC_INIT_WITH_RECT);
        Core.compare(result, source, result, Core.CMP_EQ);
        Mat foreground = new Mat(image.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        image.copyTo(foreground, result);
        return foreground;
    }
}
