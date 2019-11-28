package me;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

public class CartooningFast {

        int d = 13;
        int sigmaColor = d;
        int sigmaSpace = 7;
        int ksize = 7;

        double maxValue = 255;
        int blockSize = 9;
        int C = 2;

        Mat cartoon(Mat inputFrame) {
            Mat gray = new Mat();
            Mat co = new Mat();
            Mat mOutputFrame = new Mat();

            Imgproc.cvtColor(inputFrame, co, Imgproc.COLOR_BGR2GRAY);
            Imgproc.bilateralFilter(co, gray, d, sigmaColor, sigmaSpace);
            Imgproc.medianBlur(gray, gray, ksize);
            Imgproc.adaptiveThreshold(gray, gray, maxValue, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,
                    blockSize, C);

            Mat m = new Mat();
            Imgproc.cvtColor(gray, m, Imgproc.COLOR_GRAY2BGR);
            Core.bitwise_and(inputFrame, m, mOutputFrame);
            return mOutputFrame;
        }

    void capturing() {
        VideoCapture vc = new VideoCapture(0);
        Mat image = new Mat();
        int key = -1;
        while (key == -1) {
            try {
                vc.read(image);
                HighGui.imshow("cartoon", cartoon(image));
                key = HighGui.waitKey(30);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Imgcodecs.imwrite(String.format("cartoon/cartoon2_%s.png", new java.util.Date().getTime()), cartoon(image));
    }

    void one() {
        Mat cartoon = cartoon(Origami.grabOne());
        Imgcodecs.imwrite(String.format("cartoon/cartoon2_%s.png", new java.util.Date().getTime()), cartoon);
        HighGui.imshow("cartoon", cartoon);
        HighGui.waitKey();
    }

    public CartooningFast() {
        capturing();
        // one();
    }

    public static void main(String[] args) {
        Origami.init();
        new CartooningFast();
        System.exit(0);
    }

}