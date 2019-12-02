package me.filters;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.bitwise_and;
import static org.opencv.imgproc.Imgproc.*;

public class Cartoon implements Filter {

    int d = 13;
    int sigmaColor = d;
    int sigmaSpace = 7;
    int ksize = 7;

    double maxValue = 255;
    int blockSize = 9;
    int C = 2;

    public Cartoon(int d, int sigmaColor, int sigmaSpace, int ksize, double maxValue, int blockSize, int c) {
        this.d = d;
        this.sigmaColor = sigmaColor;
        this.sigmaSpace = sigmaSpace;
        this.ksize = ksize;
        this.maxValue = maxValue;
        this.blockSize = blockSize;
        C = c;
    }

    public Cartoon() {

    }


    public Mat apply(Mat inputFrame) {
        Mat gray = new Mat();
        Mat co = new Mat();
        Mat m = new Mat();
        Mat mOutputFrame = new Mat();

        cvtColor(inputFrame, gray, Imgproc.COLOR_BGR2GRAY);
        bilateralFilter(gray, co, d, sigmaColor, sigmaSpace);
        Mat blurred = new Mat();
//         Imgproc.medianBlur(co, blurred, ksize);
        blur(co, blurred, new Size(ksize, ksize));
        adaptiveThreshold(blurred, blurred, maxValue, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY,
                blockSize, C);

        cvtColor(blurred, m, Imgproc.COLOR_GRAY2BGR);
        bitwise_and(inputFrame, m, mOutputFrame);
        return mOutputFrame;
    }
}
