package me.filters;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static org.opencv.core.Core.transform;
import origami.Filter;

public class GreenLantern implements Filter {
    @Override
    public Mat apply(Mat source) {
        // mat is in BGR
        Mat kernel = new Mat(3, 3, CvType.CV_32F);
        kernel.put(0, 0,
                // blue
                0.114, 0., 0.,
                // green
                0., 0.587, 0.,
                // red
                0., 0., 0.299);
        Mat destination = new Mat();
        transform(source, destination, kernel);
        return destination;
    }
}
