package me.filters;

import org.opencv.core.Mat;
import origami.Filter;

public class ALTMRetinex implements Filter {
    private int r = 10;
    private double eps = 0.01;
    private double eta = 36.0;
    private double lambda = 10.0;
    private double krnlRatio = 0.1;

    @Override
    public Mat apply(Mat image) {
        return com.isaac.models.ALTMRetinex.enhance(image, r, eps, eta, lambda, krnlRatio);
    }
}