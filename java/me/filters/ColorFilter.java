package me.filters;

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

public class ColorFilter implements Filter{

    enum COLOR {
        RED, BLUE, PINK
    }

    private int low = 0;
    private int high = 20;

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
        ColorFilter cf = new ColorFilter(COLOR.RED);
        VideoCapture cap = new VideoCapture(0);
        Mat buffer = new Mat();
        while (cap.read(buffer)) {
            Mat blue = cf.apply(buffer);
            HighGui.imshow("Filter", blue);
            int key = HighGui.waitKey(1);
            if (key != -1) System.out.println(key);
        }
        cap.release();
    }

    private static void onImage(String path) {
        Mat frame = imread(path);
        ColorFilter cf = new ColorFilter(COLOR.BLUE);
        Mat result = cf.apply(frame);
        imwrite("target/filtered.jpg", result);
    }

    public ColorFilter(COLOR c) {
        switch (c) {
            case RED:
                this.low = 0;
                this.high = 20;
                break;
            case BLUE:
                this.low = 200;
                this.high = 240;
                break;
            case PINK:
                this.low = 300;
                this.high = 320;
                break;
            default:
                ;
        }
    }

    public Mat apply(Mat frame) {
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




}
