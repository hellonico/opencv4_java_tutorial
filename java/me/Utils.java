package me;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.scijava.nativelib.NativeLoader;

public class Utils {

    static void initCV() {
        try {
            NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    static Mat grabOne() {
        VideoCapture vc = new VideoCapture(0);
        Mat img1 = new Mat();
        vc.read(img1);
        vc.read(img1);
        vc.release();
        return img1;
    }
}