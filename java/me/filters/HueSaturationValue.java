package me;

import me.filters.Filter;
import org.opencv.core.Mat;
import origami.Origami;

import static org.opencv.core.Core.multiply;
import static org.opencv.core.CvType.CV_64F;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.*;

public class HueSaturationValue {

    static Mat filter(Mat marcel, int cs1, int cs2, double... values) {
        Mat m = new Mat(1, 3, CV_64F);
        m.put(0, 0, values);
        if (cs1 != -1) cvtColor(marcel, marcel, cs1);
        multiply(marcel, m, marcel);
        if (cs2 != -1) cvtColor(marcel, marcel, cs2);
        return marcel;
    }

    public static void main(String... args) {
        Origami.init();
        Mat marcel = imread("data/marcel2019.jpg");
        imwrite("lomo.jpg", new Lomo().apply(marcel.clone()));
        imwrite("nashville.jpg", new Nashville().apply(marcel.clone()));
        imwrite("gotham.jpg", new Gotham().apply(marcel.clone()));
    }

    public static class Nashville implements Filter {
        @Override
        public Mat apply(Mat marcel) {
            filter(marcel, COLOR_BGR2HSV, COLOR_HSV2BGR, 1.0, 1.5, 1.0);
            marcel.convertTo(marcel, -1, 1.5, -30);
            return marcel;
        }
    }

    public static class Gotham implements Filter {
        @Override
        public Mat apply(Mat marcel) {
            filter(marcel, COLOR_BGR2HSV, COLOR_HSV2BGR, 1.0, 0.2, 1.0);
            marcel.convertTo(marcel, -1, 1.3, -20);
            return marcel;
        }
    }

    public static class Lomo implements Filter {

        @Override
        public Mat apply(Mat in) {
            filter(in, -1, -1, 1.0, 1.33, 1.33);
            return in;
        }
    }

}