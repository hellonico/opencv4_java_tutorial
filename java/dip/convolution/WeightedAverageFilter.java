package dip.convolution;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.io.IOException;

public class WeightedAverageFilter {
    public static void main(String[] args) throws IOException {
        Origami.init();

        int kernelSize = 9;

        Mat source = Imgcodecs.imread("data/dip/grayscale.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Mat kernel = Mat.ones(kernelSize, kernelSize, CvType.CV_32F);

        for (int i = 0; i < kernel.rows(); i++) {
            for (int j = 0; j < kernel.cols(); j++) {

                double[] m = kernel.get(i, j);

                for (int k = 0; k < m.length; k++) {

                    if (i == 1 && j == 1) {
                        m[k] = 10 / 18;
                    } else {
                        m[k] = m[k] / (18);
                    }
                }
                kernel.put(i, j, m);

            }
        }

        Imgproc.filter2D(source, destination, -1, kernel);
        Imgcodecs.imwrite("weightedaveragefilter.jpg", destination);

    }
}
