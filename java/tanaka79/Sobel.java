package tanaka79;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class Sobel {
    public static void main(String[] args) {
        Origami.init();
        Mat gray = Imgcodecs.imread("data/lupin3.jpeg", 0);
        Imgproc.Sobel(gray, gray, gray.depth(), 2, 2);
        Imgcodecs.imwrite("tanaka.jpg", gray);
    }
}
