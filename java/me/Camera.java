package me;

import me.filters.Filter;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import origami.ImShow;
import origami.Origami;

import java.util.Arrays;
import java.util.List;

public class Camera {
    private static Class myFilter= me.filters.Vintage.class;

    public static void main(String[] args) throws Exception {
        Origami.init();

        VideoCapture cap = new VideoCapture(0);

        Mat matFrame = new Mat();
        ImShow ims = new ImShow("Camera", 800, 300);

        Filter cf = (Filter) Class.forName(myFilter.getName()).getConstructors()[0].newInstance();

        while (cap.grab()) {
            cap.retrieve(matFrame);
            List<Mat> mats = Arrays.asList(matFrame, cf.apply(matFrame));
            Mat result = new Mat();
            Core.hconcat(mats,result);
            ims.showImage(result);
        }
        cap.release();
    }

}
