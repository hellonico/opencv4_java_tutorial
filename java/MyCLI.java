import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Filter;
import origami.Origami;

import java.io.IOException;

public class MyCLI {

    public static void main(String...args) throws IOException {
        Origami.init();
        Mat m = Origami.urlToMat(args[0]);
        Filter f = Origami.StringToFilter(args[1]);
        Mat o = f.apply(m);
        Imgcodecs.imwrite("output.png",o);
    }
}