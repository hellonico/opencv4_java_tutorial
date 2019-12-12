package me;

import java.util.function.Function;

import org.opencv.core.Mat;

import origami.Camera;

public class Camera2 extends Camera {

    public Camera2() {
        super();
    }

    @Override
    public Camera filter(Function<Mat, Mat> filter) {
        return super.filter(new Compare(filter));
    }
}
