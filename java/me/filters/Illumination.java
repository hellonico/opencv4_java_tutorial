package me.filters;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static org.opencv.photo.Photo.illuminationChange;

public class Illumination implements Filter {
    public float alpha = 2f;
    public float beta = 0.4f;

    @Override
    public Mat apply(Mat in) {
        Mat dst = new Mat();
        Mat mask = Mat.zeros(in.size(), CvType.CV_8UC1);
        illuminationChange(in, mask, dst, alpha, beta);
        return dst;
    }
}
