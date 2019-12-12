package webcam;

import me.filters.android.Cartoon2;
import origami.Camera;
import origami.Origami;

public class SimpleCam {

    public static void main(String args[]) throws InterruptedException {
        Origami.init();
//        new Camera().filter(new HueSaturationValue.Lomo()).run();
        new Camera().filter(new Cartoon2()).run();
    }

}