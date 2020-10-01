package me;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractor;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import origami.Origami;

class BackgroundSubtraction {
    public void run(String[] args) {
//        String input = args.length > 0 ? args[0] : "../data/vtest.avi";
//        String input = args.length > 0 ? args[0] : 0;
        Integer input = 0;
        boolean useMOG2 = args.length > 1 ? args[1] == "MOG2" : true;
        BackgroundSubtractor backSub;
        if (useMOG2) {
            backSub = Video.createBackgroundSubtractorMOG2();
        } else {
            backSub = Video.createBackgroundSubtractorKNN();
        }
        VideoCapture capture = new VideoCapture();
        capture.open(input);
        if (!capture.isOpened()) {
            System.err.println("Unable to open: " + input);
            System.exit(0);
        }
        Mat frame = new Mat(), fgMask = new Mat();
        while (true) {
            capture.read(frame);
            if (frame.empty()) {
                break;
            }
            // update the background model
            backSub.apply(frame, fgMask);
            // get the frame number and write it on the current frame
            Imgproc.rectangle(frame, new Point(10, 2), new Point(100, 20), new Scalar(255, 255, 255), -1);
            String frameNumberString = String.format("%d", (int)capture.get(Videoio.CAP_PROP_POS_FRAMES));
            Imgproc.putText(frame, frameNumberString, new Point(15, 15),Imgproc.FONT_HERSHEY_PLAIN, 0.5,
                    new Scalar(0, 0, 0));
            // show the current frame and the fg masks
            HighGui.imshow("Frame", frame);
            HighGui.imshow("FG Mask", fgMask);
            // get the input from the keyboard
            int keyboard = HighGui.waitKey(30);
            if (keyboard == 'q' || keyboard == 27) {
                break;
            }
        }
        HighGui.waitKey();
        System.exit(0);
    }
}
public class BackgroundSubstration {
    public static void main(String[] args) {
        Origami.init();
        new BackgroundSubtraction().run(args);
    }
}