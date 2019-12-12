package me.filters;

import org.opencv.core.Mat;
import origami.Filter;
import static org.opencv.photo.Photo.fastNlMeansDenoising;

public class FastDenoising implements Filter {
    int block_size = 7;
    int search_window = 21;

    @Override
    public Mat apply(Mat in) {
        Mat dst = new Mat();
        fastNlMeansDenoising(in, dst, block_size, search_window);
        return dst;

    }
}
