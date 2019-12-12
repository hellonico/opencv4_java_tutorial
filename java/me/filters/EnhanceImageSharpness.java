package me.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import origami.Filter;

public class EnhanceImageSharpness implements Filter {
    public double alpha = 1.5;
    public double beta = -0.5;
    public double gamma = 0;

    public Mat apply(Mat source) {
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.GaussianBlur(source, destination, new Size(1, 1), 10);
        Core.addWeighted(source, alpha, destination, beta, gamma, destination);
        return destination;
    }
}
