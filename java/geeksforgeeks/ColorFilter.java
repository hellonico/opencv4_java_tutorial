package geeksforgeeks;

import com.isaac.utils.ImShow;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

import static org.opencv.core.Core.bitwise_and;
import static org.opencv.core.Core.inRange;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;

public class ColorFilter {

    /**
     * https://i.stack.imgur.com/SobpV.jpg
     * divided by 2
     * https://stackoverflow.com/questions/17878254/opencv-python-cant-detect-blue-objects
     */
    public static void main(String... args) {
        Origami.init();

        onImage("data/geeksforgeeks/screenshot-3091-300x238.png");
        onVideo();
    }

    private static void onVideo() {
        ColorFilter cf = new ColorFilter();
        VideoCapture cap = new VideoCapture(0);
        Mat buffer = new Mat();
        while (cap.read(buffer)) {
            Mat blue = cf.red(buffer);
            HighGui.imshow("Filter", blue);
            int key = HighGui.waitKey(1);
            if (key != -1) System.out.println(key);
        }
        cap.release();
    }

    private static void onImage(String path) {
        Mat frame = imread(path);
        ColorFilter cf = new ColorFilter();
        Mat result = cf.blue(frame);
        imwrite("target/filtered.jpg", result);
    }

    public Mat filter(Mat frame, int low, int high) {
        Mat result = new Mat();
        Mat hsv = new Mat();
        cvtColor(frame, hsv, COLOR_BGR2HSV);
        Mat mask = new Mat();
        Scalar lower = new Scalar(low >> 1, 100, 0);
        Scalar upper = new Scalar(high >> 1, 255, 255);
        inRange(hsv, lower, upper, mask);
        bitwise_and(frame, frame, result, mask);
        return result;
    }

    public Mat red(Mat frame) {
        return filter(frame, 0 ,20);
    }

    public Mat blue(Mat frame) {
        return filter(frame, 200, 240);
    }
    public Mat pink(Mat frame) {
        return filter(frame, 300,300);
    }


}
