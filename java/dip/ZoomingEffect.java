package dip;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.io.IOException;

public class ZoomingEffect {

    public static void main(String[] args) throws IOException {
        Origami.init();

        Mat source = Imgcodecs.imread("data/dip/grayscale.jpg", Imgcodecs.IMREAD_GRAYSCALE);

        int zoomingFactor = 3;
        Mat destination = new Mat(source.rows(), source.cols(), source.type());
        Imgproc.resize(source, destination, destination.size(), zoomingFactor, zoomingFactor, Imgproc.INTER_LINEAR);
        Imgcodecs.imwrite("zoomed2.jpg", destination);

    }
}
