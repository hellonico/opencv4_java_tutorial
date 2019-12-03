package me.filters;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class EnhanceImageSharpness implements Filter {
    double alpha = 1.5;
    double beta = -0.5;
    double gamma = 0;

    public Mat apply(Mat source) {
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.GaussianBlur(source, destination, new Size(1, 1), 10);
        Core.addWeighted(source, alpha, destination, beta, gamma, destination);
        return destination;
    }
}
