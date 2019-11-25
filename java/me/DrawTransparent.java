package me;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.scijava.nativelib.NativeLoader;

public class DrawTransparent {

    static void drawTransparency(Mat frame, Mat transp, int xPos, int yPos) {
        List<Mat> layers = new ArrayList<Mat>();
        Core.split(transp, layers);
        Mat mask = layers.remove(3);
        Core.merge(layers, transp);
        Mat submat = frame.submat(yPos, yPos + transp.rows(), xPos, xPos + transp.cols());
        transp.copyTo(submat, mask);
    }

    static void weightedTransparency(Mat frame, Mat transp, int xPos, int yPos) {
        List<Mat> layers = new ArrayList<Mat>();
        Core.split(transp, layers);

        // transparency used as the mask
        Mat mask = layers.remove(3);
        // we need an inverted mask later on
        Core.bitwise_not(mask, mask);

        Mat merged = new Mat();
        Core.merge(layers, merged);

        Mat submat = frame.submat(yPos, yPos + transp.rows(), xPos, xPos + transp.cols());
        Mat source = submat.clone();

        Core.addWeighted(merged, 0.5, source, 0.5, 0, submat);

        source.copyTo(submat, mask);
    }

    public static void marcelAndWhool() {
        Mat marcel = Imgcodecs.imread("data/marcel2019.jpg");
        Mat whool = Imgcodecs.imread("data/tp/whool.png", Imgcodecs.IMREAD_UNCHANGED);
        drawTransparency(marcel, whool, 50, 50);
        Imgcodecs.imwrite("target/marcelandwhool.jpg", marcel);
    }

    public static void marcelAndSheep() {
        Mat marcel = Imgcodecs.imread("data/marcel2019.jpg");
        Mat sheep = Imgcodecs.imread("data/tp/sheep.png", Imgcodecs.IMREAD_UNCHANGED);
        weightedTransparency(marcel, sheep, 50, 50);
        sheep = Imgcodecs.imread("data/tp/sheep.png", Imgcodecs.IMREAD_UNCHANGED);
        weightedTransparency(marcel, sheep, 400, 500);
        Imgcodecs.imwrite("target/marcelandsheep.jpg", marcel);
    }

    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        marcelAndWhool();
        marcelAndSheep();
    }
}