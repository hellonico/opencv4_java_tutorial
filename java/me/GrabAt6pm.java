package me;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import origami.Camera;
import origami.Filter;
import origami.Origami;

public class GrabAt6pm {
    static class MyFilter implements Filter {

        @Override
        public Mat apply(Mat mat) {
            Mat mask = new Mat();
            Mat local_image = mat.clone();
            Rect rect = new Rect(new Point(400,400) , new Size(200,200));
            Imgproc.grabCut(local_image, mask , rect, new Mat(), new Mat(), 5);
            Mat fg_mask = mask.clone();
            Mat pfg_mask =mask.clone();
            Mat source = new Mat(1, 1, CvType.CV_8U, new Scalar(3.0));
            Core.compare(mask,source,pfg_mask, Core.CMP_EQ);
            source = new Mat(1, 1, CvType.CV_8U, new Scalar(1.0));
            Core.compare(mask,source,fg_mask,Core.CMP_EQ);
            Mat fg_foreground = new Mat(mat.size(),mat.type(),new Scalar(0,0,0));
            Mat pfg_foreground = new Mat(mat.size(),mat.type(),new Scalar(0,0,0));
            //display.show(pfg_mask);
            //display.show(fg_mask);
            Mat finalMask = new Mat();
            Core.bitwise_or(pfg_mask, fg_mask, finalMask);
            //display.show(finalMask);
            mat.copyTo(fg_foreground,fg_mask);
            mat.copyTo(pfg_foreground,pfg_mask);
            Mat foreground = new Mat(fg_foreground.size(),CvType.CV_8UC3);
            Core.bitwise_or(fg_foreground, pfg_foreground, foreground);
            return foreground;
        }
    }

    public static void main(String... args) {
        Origami.init();
        new Camera().filter(new MyFilter()).run();
    }

}
