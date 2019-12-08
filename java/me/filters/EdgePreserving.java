package me.filters;

import org.opencv.core.Mat;

import static org.opencv.photo.Photo.*;

public class EdgePreserving implements Filter {
    public int flags = RECURS_FILTER;
//    int flags = NORMCONV_FILTER;
    public float sigma_s = 60;
    public float sigma_r = 0.4f;

    @Override
    public Mat apply(Mat in) {
        Mat dst = new Mat();
        edgePreservingFilter(in, dst, flags, sigma_s, sigma_r);
        return dst;
    }
}
