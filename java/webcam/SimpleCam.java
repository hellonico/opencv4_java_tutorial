package webcam;

import me.filters.HueSaturationValue;
import me.filters.android.Cartoon;
import origami.Camera;
import origami.Filter;
import origami.Origami;

public class SimpleCam {

    public static void main(String args[]) throws InterruptedException {
        Origami.init();
//        new Camera().filter(new HueSaturationValue.Lomo()).run();
        new Camera().filter(new Cartoon()).run();
    }

}