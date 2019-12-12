package me.filters;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import origami.Filter;
import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_PLAIN;
import static org.opencv.imgproc.Imgproc.putText;

public class FPS implements Filter {

    long start = System.currentTimeMillis();
    int count = 0;

    public Point org = new Point(50, 50);
    public int fontFace = FONT_HERSHEY_PLAIN;
    public double fontScale = 2.0;
    public Scalar color = new Scalar(0, 0, 0);
    public int thickness = 2;

    @Override
    public Mat apply(Mat in) {
        count++;
        String text = "FPS: " + count / (1 + ((System.currentTimeMillis() - start) / 1000));
        putText(in, text, org, fontFace, fontScale, color, thickness);
        return in;
    }
}
