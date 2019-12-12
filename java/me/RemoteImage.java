package me;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Filter;
import origami.Origami;
import origami.filters.Vintage;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;

public class RemoteImage {

    public static void main(String[] args) throws Exception {
        Origami.init();
        String url = "https://raw.githubusercontent.com/hellonico/opencv4_java_tutorial/master/data/marcel2019.jpg";
        Mat mat = Origami.urlToMat(args != null ? url : args[0], Imgcodecs.IMREAD_UNCHANGED);
        Filter f = new Vintage();
        imshow("url", f.apply(mat));
        waitKey();
    }
}