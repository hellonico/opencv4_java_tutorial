package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class ApplyingWatermarkWithROI {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Mat waterMark = Imgcodecs.imread("data/dip/watermark.jpg",  Imgcodecs.CV_LOAD_IMAGE_COLOR);
        Rect ROI = new Rect(20,20  ,  waterMark.cols(), waterMark.rows());
        Core.addWeighted(source.submat(ROI), 0.8, waterMark, 0.2, 1,  source.submat(ROI));
        Imgcodecs.imwrite("watermarkedROI.jpg", source);
    }
}
