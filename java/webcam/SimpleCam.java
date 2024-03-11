package webcam;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.photo.Photo;
import origami.Camera;
import origami.Origami;
import origami.filters.HueSaturationValue;

import java.util.function.Function;

public class SimpleCam {

    public static void main(String args[]) throws InterruptedException {
        Origami.init();
//        new Camera().filter(new HueSaturationValue.Lomo()).run();
//        new Camera().filter(new Cartoon2()).run();
        new Camera().filter(new Function<Mat, Mat>() {
            @Override
            public Mat apply(Mat im) {
                // Photo.fastNlMeansDenoising(im, im);
                // identity!
                // doing nothing here.
                return im;
            }
        }).run();

    }

}