package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class ApplyingWatermark {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Imgproc.putText(source, "dip.hellonico.info", new Point(source.rows() / 2, source.cols() / 2), Imgproc.FONT_ITALIC, new Double(1), new Scalar(255));
        Imgcodecs.imwrite("watermarked.jpg", source);
    }
}
