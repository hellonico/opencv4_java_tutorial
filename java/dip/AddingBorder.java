package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class AddingBorder {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg");
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        int top, bottom, left, right;
        top = (int) (0.05 * source.rows());
        bottom = (int) (0.05 * source.rows());
        left = (int) (0.05 * source.cols());
        right = (int) (0.05 * source.cols());

        Core.copyMakeBorder(source, destination, top, bottom, left, right, Core.BORDER_WRAP);
        Imgcodecs.imwrite("borderWrap.jpg", destination);

        Core.copyMakeBorder(source, destination, top, bottom, left, right, Core.BORDER_REFLECT);
        Imgcodecs.imwrite("borderReflect.jpg", destination);

        Core.copyMakeBorder(source, destination, top, bottom, left, right, Core.BORDER_REPLICATE);
        Imgcodecs.imwrite("borderReplicate.jpg", destination);

    }
}
