package me.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Thresh implements Filter{

    int sensitivity = 100;
    public Thresh() {

    }
    public Thresh(int _sensitivity) {
        this.sensitivity = _sensitivity;
    }

    public Mat apply(Mat img) {
        Mat threshed = new Mat();
        Imgproc.threshold(img, threshed, sensitivity, 255, Imgproc.THRESH_BINARY);
        return threshed;
    }
}