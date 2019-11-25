package me;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

public class RemoteImage {

    public static void main(String[] args) throws Exception {
        Origami.init();
        Mat mat = Origami.urlToMat(args[0], Imgcodecs.IMREAD_UNCHANGED);
        imshow("url", mat);
        waitKey();
    }
}