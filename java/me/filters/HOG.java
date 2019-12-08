package me.filters;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

import java.util.List;

import static org.opencv.imgproc.Imgproc.*;

public class HOG implements Filter{

    HOGDescriptor _HOG = new HOGDescriptor();
    public HOG() {
        _HOG.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
    }



    @Override
    public Mat apply(Mat matFrame) {
        Mat grey = new Mat();
        Imgproc.cvtColor(matFrame, grey, Imgproc.COLOR_BGR2GRAY);

        Imgproc.adaptiveThreshold(grey, grey, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY_INV, 7,
                10);

        Size ksize = new Size(15, 15);
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, ksize);

        Imgproc.morphologyEx(matFrame, matFrame, Imgproc.MORPH_CLOSE, kernel);


        MatOfRect foundLocations = new MatOfRect();
        MatOfDouble foundWeights = new MatOfDouble();

        _HOG.detectMultiScale(grey, foundLocations, foundWeights, 0, new Size(8, 8), new Size(32, 32),
                1.05, 8, false);

        List<Rect> rectangles = foundLocations.toList();

        for (Rect rectangle : rectangles) {
            rectangle(matFrame, new Point(rectangle.x, rectangle.y),
                    new Point(rectangle.x + rectangle.width,
                            rectangle.y + rectangle.height),
                    new Scalar(255, 0, 0), 2, 1, 0);
        }
        return matFrame;
//        Imgproc.cvtColor(grey, grey, COLOR_GRAY2BGR);
//        return grey;
    }
}
