package me.filters;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.bitwise_not;
import static org.opencv.core.Core.bitwise_or;
import static org.opencv.imgproc.Imgproc.COLOR_GRAY2RGB;
import origami.Filter;

public class Canny implements Filter {
    public boolean inverted = true;
    public int threshold1 = 100;
    public int threshold2 = 200;

    @Override
    public Mat apply(Mat in) {
        Mat dst = new Mat();
        Imgproc.Canny(in, dst, threshold1, threshold2);
        if (inverted) {
            bitwise_not(dst, dst, new Mat());
        }
        Imgproc.cvtColor(dst, dst, COLOR_GRAY2RGB);
        return dst;
    }
}
