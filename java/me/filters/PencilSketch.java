package me.filters;

import org.opencv.core.Mat;

import static org.opencv.photo.Photo.pencilSketch;

public class PencilSketch implements Filter {
    float sigma_s = 60;
    float sigma_r = 0.07f;
    float shade_factor = 0.05f;
    boolean gray = false;

    @Override
    public Mat apply(Mat in) {
        Mat dst = new Mat();
        Mat dst2 = new Mat();
        pencilSketch(in, dst, dst2, sigma_s, sigma_r, shade_factor);
        return gray ? dst : dst2;
    }
}
