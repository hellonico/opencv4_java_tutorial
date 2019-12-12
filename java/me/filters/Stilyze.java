package me.filters;

import org.opencv.core.Mat;

import static org.opencv.photo.Photo.stylization;
import origami.Filter;

public class Stilyze implements Filter {
    float sigma_s = 60;
    // float sigma_r=45;
    float sigma_r = 0.07f;

    @Override
    public Mat apply(Mat in) {
        Mat dst = new Mat();
        stylization(in, dst, sigma_s, sigma_r);
        return dst;
    }
}
