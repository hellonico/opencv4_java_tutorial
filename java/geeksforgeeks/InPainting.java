package geeksforgeeks;

import static org.opencv.core.Core.NATIVE_LIBRARY_NAME;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.photo.Photo.INPAINT_NS;
import static org.opencv.photo.Photo.inpaint;
import static org.scijava.nativelib.NativeLoader.loadLibrary;

import java.io.IOException;

import org.opencv.core.Mat;

/**
 * https://www.geeksforgeeks.org/introduction-to-opencv/
 */
public class InPainting {

    public static void main(String[] args) throws IOException {
        loadLibrary(NATIVE_LIBRARY_NAME);
        Mat img = imread("data/cat_damaged.png");
        Mat mask = imread("data/cat_mask.png", 0);
        Mat dst = new Mat();
        inpaint(img, mask, dst, 3, INPAINT_NS);
        imwrite("data/cat_inpainted.png", dst);
    }

}
