package me;

import me.filters.HueSaturationValue;
import org.opencv.core.Mat;
import origami.Origami;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class FunFilters {

    public static void main(String... args) {
        Origami.init();
        Mat marcel = imread("data/marcel2019.jpg");
        imwrite("lomo.jpg", new HueSaturationValue.Lomo().apply(marcel.clone()));
        imwrite("nashville.jpg", new HueSaturationValue.Nashville().apply(marcel.clone()));
        imwrite("gotham.jpg", new HueSaturationValue.Gotham().apply(marcel.clone()));
    }
}
