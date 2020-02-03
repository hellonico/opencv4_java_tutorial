package dip;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.io.IOException;

public class EnhanceImageContrast {

    public static void main(String[] args) throws IOException {
        Origami.init();
        Mat source = Imgcodecs.imread("data/dip/grayscale.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.equalizeHist(source, destination);
        Imgcodecs.imwrite("contrast.jpg", destination);
    }
}
