package me;

import me.filters.Cartoon;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

import javax.swing.*;

public class CartooningFast {



    void capturing() {
        VideoCapture vc = new VideoCapture(0);
        if(!vc.isOpened()) return;
        Mat image = new Mat();
        int key = -1;
        HighGui.createJFrame("cartoon", JFrame.EXIT_ON_CLOSE);
        Cartoon cf = new Cartoon();
        while (key == -1) {
                vc.read(image);
                HighGui.imshow("cartoon", cf.apply(image));
                key = HighGui.waitKey(30);
        }
        Imgcodecs.imwrite(String.format("cartoon/cartoon2_%s.png", new java.util.Date().getTime()), cf.apply(image));
    }

    void one() {
        Cartoon cf = new Cartoon();
        Mat cartoon = cf.apply(Origami.grabOne());
        Imgcodecs.imwrite(String.format("cartoon/cartoon2_%s.png", new java.util.Date().getTime()), cartoon);
        HighGui.imshow("cartoon", cartoon);
        HighGui.waitKey();
    }

    public CartooningFast() {
        capturing();
//         one();
    }

    public static void main(String[] args) {
        Origami.init();
        new CartooningFast();
        System.exit(0);
    }

}