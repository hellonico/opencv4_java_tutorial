package dip;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

import java.io.IOException;

public class EnhanceImageBrightness {

    static double alpha = 2;
    static double beta = 50;

    public static void main(String[] args) throws IOException {
        Origami.init();
        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.IMREAD_COLOR);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        source.convertTo(destination, -1, alpha, beta);
        Imgcodecs.imwrite("brightWithAlpha2Beta50.jpg", destination);

    }
}
