package me;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;
import origami.filters.LUTCartoon;

import java.io.File;
import java.util.Date;

public class Cartooning {

    public static void main(String[] args) {
        Origami.init();
        Mat inputImage = args.length > 0 ? Imgcodecs.imread(args[0]) : Origami.grabOne();
        LUTCartoon cartoon = new LUTCartoon();
        Mat result = cartoon.apply(inputImage);
        new File("cartoon").mkdirs();
        Imgcodecs.imwrite(String.format("cartoon/cartoon_%s.jpg", new Date().getTime()), result);
    }

}