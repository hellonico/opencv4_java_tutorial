package geeksforgeeks;

import org.opencv.core.Mat;

import java.io.IOException;

import static org.opencv.core.Core.NATIVE_LIBRARY_NAME;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.photo.Photo.INPAINT_NS;
import static org.opencv.photo.Photo.inpaint;
import static org.scijava.nativelib.NativeLoader.loadLibrary;

/**
 * https://www.geeksforgeeks.org/introduction-to-opencv/
 */
public class InPainting {

    public static void main(String[] args) throws IOException {
        loadLibrary(NATIVE_LIBRARY_NAME);
        var img = imread("data/cat_damaged.png");
        var mask = imread("data/cat_mask.png", 0);
        var dst = new Mat();
        inpaint(img, mask, dst, 3, INPAINT_NS);
        imwrite("data/cat_inpainted.png", dst);
    }

}
