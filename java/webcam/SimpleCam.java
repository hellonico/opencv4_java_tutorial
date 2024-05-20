package webcam;

import org.opencv.core.Mat;
import origami.Camera;
import origami.Filter;
import origami.Origami;

public class SimpleCam {

    public static void main(String args[]) throws InterruptedException {
        Origami.init();
//        new Camera().filter(new HueSaturationValue.Lomo()).run();
//        new Camera().filter(new Cartoon2()).run();
        new Camera().filter(new Filter() {
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