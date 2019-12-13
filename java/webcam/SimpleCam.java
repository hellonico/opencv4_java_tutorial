package webcam;

import origami.Camera;
import origami.Origami;
import origami.filters.Cartoon2;

public class SimpleCam {

    public static void main(String args[]) throws InterruptedException {
        Origami.init();
//        new Camera().filter(new HueSaturationValue.Lomo()).run();
        new Camera().filter(new Cartoon2()).run();
    }

}