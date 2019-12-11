package me;

import static java.util.Arrays.asList;

import java.util.List;

import me.filters.*;
import me.filters.android.Blending;
import me.filters.android.Cartoon;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import origami.ImShow;
import origami.Origami;

public class Camera {
    VideoCapture cap = new VideoCapture(0);
    ImShow ims = new ImShow("Origami", 800, 600);
    Filter cf = new Filters(Cartoon.class, FPS.class);

    public Camera() {

    }

    public void two() {
        Mat matFrame = new Mat();
        while (cap.grab()) {
            cap.retrieve(matFrame);
            List<Mat> mats = asList(matFrame, cf.apply(matFrame));
            Mat result = new Mat();
            Core.hconcat(mats, result);
            ims.showImage(result);
        }
    }
    public void one() {
        Mat matFrame = new Mat();
        while (cap.grab()) {
            cap.retrieve(matFrame);
            ims.showImage(cf.apply(matFrame));
        }
    }

    public static void main(String[] args) throws Exception {
        Origami.init();
        new Camera().one();
    }

}
