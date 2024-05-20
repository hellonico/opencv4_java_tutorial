import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

public class HQCamera {
    public static void main(String[] args) {
        Origami.init();
        // Initialize the video capture
        VideoCapture capture = new VideoCapture(0); // 0 for default camera

        // Check if the camera is opened successfully
        if (!capture.isOpened()) {
            System.out.println("Error: Camera not opened!");
            return;
        }
        System.out.printf("Opencv Version: %s\n", Core.VERSION);
        // Set the camera properties to the best resolution
        capture.set(3, 1280);
        capture.set(4, 720);
//        1966 × 1312 pixels
        // Capture a frame
        Mat frame = new Mat();
        capture.read(frame);

        // Release the camera
        capture.release();

        // Save the frame as an image
        String filename = "captured_image.jpg";
        Imgcodecs.imwrite(filename, frame);

        System.out.println("Image captured and saved as: " + filename);
    }
}
