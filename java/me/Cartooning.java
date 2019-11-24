package me;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Cartooning {

    public static void main(String[] args) {
        Utils.initCV();
        Mat inputImage = args.length > 0 ? Imgcodecs.imread(args[0]) : Utils.grabOne();
        Mat result = cartoon(inputImage);
        new File("cartoon").mkdirs();
        Imgcodecs.imwrite(String.format("cartoon/cartoon_%s.jpg", new Date().getTime()), result);
    }

    static Mat createLUT(int numColors) {
        // When numColors=1 the LUT will only have 1 color which is black.
        if (numColors < 0 || numColors > 256) {
            System.out.println("Invalid Number of Colors. It must be between 0 and 256 inclusive.");
            return null;
        }

        Mat lookupTable = Mat.zeros(new Size(1, 256), CvType.CV_8UC1);

        int startIdx = 0;
        for (int x = 0; x < 256; x += 256.0 / numColors) {
            lookupTable.put(x, 0, x);
            for (int y = startIdx; y < x; y++) {
                if (lookupTable.get(y, 0)[0] == 0) {
                    lookupTable.put(y, 0, lookupTable.get(x, 0));
                }
            }
            startIdx = x;
        }
        return lookupTable;
    }

    public static Mat reduceColors(Mat img, int numRed, int numGreen, int numBlue) {
        Mat redLUT = createLUT(numRed);
        Mat greenLUT = createLUT(numGreen);
        Mat blueLUT = createLUT(numBlue);

        List<Mat> BGR = new ArrayList<>(3);
        Core.split(img, BGR);

        Core.LUT(BGR.get(0), blueLUT, BGR.get(0));
        Core.LUT(BGR.get(1), greenLUT, BGR.get(1));
        Core.LUT(BGR.get(2), redLUT, BGR.get(2));

        Core.merge(BGR, img);

        return img;
    }

    public static Mat cartoon(Mat img1) {
        Mat reducedColorImage = reduceColors(img1, 80, 80, 80);
        Mat result = new Mat();
        Imgproc.cvtColor(img1, result, Imgproc.COLOR_BGR2GRAY);
        Imgproc.medianBlur(result, result, 15);
        Imgproc.adaptiveThreshold(result, result, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 2);
        Imgproc.cvtColor(result, result, Imgproc.COLOR_GRAY2BGR);
        Core.bitwise_and(reducedColorImage, result, result);
        return result;
    }
}