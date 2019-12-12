package me;

import org.opencv.core.Mat;
import origami.Camera;

import java.util.function.Function;

public class Camera2 extends Camera {

    public Camera2() {
        super();
    }

    @Override
    public Camera filter(Function<Mat, Mat> filter) {
        return super.filter(new Compare(filter));
    }
}
