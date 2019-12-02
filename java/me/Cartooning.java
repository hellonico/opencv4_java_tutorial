package me;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.filters.LUTCartoon;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

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