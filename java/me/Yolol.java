package me;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Filter;
import origami.Origami;
import origami.filters.detect.yolo.MyYolo;

import java.io.IOException;

public class Yolol {

    public static void main(String... args) throws IOException {
        Origami.init();
        String url = "https://raw.githubusercontent.com/hellonico/origami-dnn/master/resources/marcel.jpg";
        Mat marcel = Origami.urlToMat(url);
        Filter f = new MyYolo.V2Tiny();
        Mat output = f.apply(marcel);
        Imgcodecs.imwrite("yolo.jpg",output);
    }
}
