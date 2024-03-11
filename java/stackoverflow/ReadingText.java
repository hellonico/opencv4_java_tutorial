package stackoverflow;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Filter;
import origami.Filters;
import origami.Origami;
import origami.filters.detect.EastTextDetector;


import static origami.Origami.*;

public class ReadingText {


    public static void main(String[] args) {
        init();

        Mat frame = Imgcodecs.imread("data/mdl/m2.jpg");

        Filter f = new Filters(a -> {
            Core.bitwise_not(a, a);
            return a;
        }, new EastTextDetector());

        Imgcodecs.imwrite("out.png", f.apply(frame));
    }

}