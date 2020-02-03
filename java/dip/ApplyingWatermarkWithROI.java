package dip;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

import java.io.IOException;

public class ApplyingWatermarkWithROI {
    public static void main(String[] args) throws IOException {
        Origami.init();
        Mat source = Imgcodecs.imread("data/dip/digital_image_processing.jpg", Imgcodecs.IMREAD_COLOR);
        Mat waterMark = Imgcodecs.imread("data/dip/watermark.jpg", Imgcodecs.IMREAD_COLOR);
        Rect ROI = new Rect(20, 20, waterMark.cols(), waterMark.rows());
        Core.addWeighted(source.submat(ROI), 0.8, waterMark, 0.2, 1, source.submat(ROI));
        Imgcodecs.imwrite("watermarkedROI.jpg", source);
    }
}
