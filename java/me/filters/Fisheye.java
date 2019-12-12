package me.filters;

import origami.Filter;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.features2d.Feature2D;
import org.opencv.imgproc.Imgproc;

import static org.opencv.calib3d.Calib3d.undistort;

public class Fisheye implements Filter {

    float fishVal = 600.0f;
//    float cX = 960;
    float cX = 300;
    float cY = 540;

    public Mat apply(Mat mat) {
        Mat K = new Mat(3, 3, CvType.CV_32FC1);
        K.put(0, 0, new float[]{fishVal, 0, cX});
        K.put(1, 0, new float[]{0, fishVal, cY});
        K.put(2, 0, new float[]{0, 0, 1});

        Mat D = new Mat(1, 4, CvType.CV_32FC1);
        D.put(0, 0, new float[]{0, 0, 0, 0});

        Mat Knew = K.clone();
        Knew.put(0, 0, new float[]{fishVal * 0.4f, 0.0f, cX});
        Knew.put(1, 0, new float[]{0.0f, fishVal * 0.4f, cY});
        Knew.put(2, 0, new float[]{0.0f, 0.0f, 1.0f});

        Mat dst = new Mat();
        Calib3d.fisheye_undistortImage(mat, dst, K, D, Knew);
//        Calib3d.fisheye_distortPoints(mat, dst, K, D);
        return dst;
    }

}
