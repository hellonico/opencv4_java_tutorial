package me.filters;

import org.opencv.core.Mat;

import static org.opencv.imgproc.Imgproc.INTER_LINEAR;
import static org.opencv.imgproc.Imgproc.resize;
import origami.Filter;

public class Zoom implements Filter {

    private int zoomingFactor = 5;
    private int interpolation = INTER_LINEAR;

    public Zoom() {
    }

    public Zoom(int zoomingFactor, int interpolation) {
        this.zoomingFactor = zoomingFactor;
        this.interpolation = interpolation;
    }

    @Override
    public Mat apply(Mat source) {
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        resize(source, destination, destination.size(), zoomingFactor, zoomingFactor, interpolation);
        return destination;
    }
}
