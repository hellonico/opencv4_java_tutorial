package me;

import static java.util.Arrays.asList;

import java.util.List;

import me.filters.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import origami.ImShow;
import origami.Origami;

public class Camera {

    public static void main(String[] args) throws Exception {
        Origami.init();

        VideoCapture cap = new VideoCapture(0);

        Mat matFrame = new Mat();
        ImShow ims = new ImShow("Camera", 800, 300);

//        Filter cf = new Filters(me.HueSaturationValue.Nashville.class, FPS.class);
        Filter cf = new Filters(HueSaturationValue.Pink.class, FPS.class);

        while (cap.grab()) {
            cap.retrieve(matFrame);
            List<Mat> mats = asList(matFrame, cf.apply(matFrame));
            Mat result = new Mat();
            Core.hconcat(mats, result);
            ims.showImage(result);
        }
        cap.release();
    }

}
