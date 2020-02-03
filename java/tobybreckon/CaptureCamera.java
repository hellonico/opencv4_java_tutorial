package tobybreckon;
// ********************************************************

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

// ********************************************************

public class CaptureCamera {
    static {
        try {
            // Load the native OpenCV library
            Origami.init();
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {

        // load the Core OpenCV library by name

        // create video capture device object (camera index = 0)

        VideoCapture cap = new VideoCapture(0);

        if (!cap.isOpened())
            System.out.println("error cannot any open camera");
        else
            System.out.println("found webcam: " + cap.toString());

        Mat matFrame = new Mat();

        // Grabs the next frame from video file or capturing device

        cap.grab(); // First frame maybe empty frame so skip it
        cap.grab();

        // Decodes and returns the grabbed video frame.

        cap.retrieve(matFrame);

        // display image

        Imshow ims = new Imshow("From Camera ....");
        ims.showImage(matFrame);

        // close down the camera correctly

        cap.release();

    }
}

// ********************************************************
