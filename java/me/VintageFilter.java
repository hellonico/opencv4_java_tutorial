package me;

import imagecomparison.CV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.*;
import static org.opencv.core.Core.*;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;

public class VintageFilter {
    private final int sigma;

    public static void main(String... args) {
        Origami.init();
        /**
         * // https://stackoverflow.com/questions/27212167/unable-to-multiply-matrix-in-opencv-java
         */

        Mat marcel = imread("data/marcel.jpg");
        VintageFilter filter = new VintageFilter();
        imwrite("vintage.jpeg", filter.apply(marcel));
    }

    public VintageFilter(int sigma) {
        this.sigma = sigma;
    }

    public VintageFilter() {
        this(200);
    }

    public Mat apply(Mat marcel) {
        Mat kernelx = getGaussianKernel(marcel.cols(), sigma);
        Mat kernely = getGaussianKernel(marcel.rows(), sigma);
        Mat kernel = new Mat();
        gemm(kernely, kernelx.t(), 1, new Mat(), 0, kernel);
        Mat filter = new Mat();
        divide(kernel, new Scalar(norm(kernel)), filter);
        multiply(filter, new Scalar(255.0), filter);

        Mat vintage = marcel.clone();
        List<Mat> channels = new ArrayList<Mat>();
        split(vintage, channels);
        for (Mat c : channels) {
            c.convertTo(c, CvType.CV_64F);
            multiply(c, filter, c);
        }
        merge(channels, vintage);
        return vintage;
    }
}
