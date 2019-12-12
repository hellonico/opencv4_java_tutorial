package me.filters;

import org.opencv.core.Mat;

import static org.opencv.photo.Photo.detailEnhance;
import origami.Filter;

public class DetailEnhance implements Filter {
    float sigma_s = 10;
    float sigma_r = 0.15f;

    @Override
    public Mat apply(Mat src) {
        Mat dst = new Mat();
        detailEnhance(src, dst, sigma_s, sigma_r);
        return dst;
    }
}
