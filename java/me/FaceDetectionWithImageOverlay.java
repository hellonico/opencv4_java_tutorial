package me;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.scijava.nativelib.NativeLoader;

import origami.Origami;


class My_Panel extends JPanel {
    private static final long serialVersionUID = 1L;
    BufferedImage image;
    // Create a constructor method

    public My_Panel() {
        super();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.image == null)
            return;
        g.drawImage(this.image, 10, 10, 2 * this.image.getWidth(), 2 * this.image.getHeight(), null);
        g.drawString("This is my custom Panel!", 10, 20);
    }
}

class processor {
    private CascadeClassifier face_cascade;

    // Create a constructor method
    public processor() {
        face_cascade = new CascadeClassifier("data/haarcascades/haarcascade_frontalface_alt.xml");
        if (face_cascade.empty()) {
            System.out.println("--(!)Error loading A\n");
            return;
        } else {
            System.out.println("Face classifier loooaaaaaded up");
        }
    }

    static void drawTransparency(Mat frame, Mat transp, int xPos, int yPos) {
        List<Mat> layers = new ArrayList<Mat>();
        Core.split(transp, layers); 
        Mat mask = layers.remove(3);
        Core.merge(layers, transp);  
        Mat submat = frame.submat(yPos, yPos + transp.rows(),xPos, xPos + transp.cols() );
        transp.copyTo(submat, mask);
    }

    Mat mask = Imgcodecs.imread("data/masquerade_mask.png", Imgcodecs.IMREAD_UNCHANGED);

    public Mat detect(Mat inputframe) {
        long startTime = System.nanoTime();
        Mat mRgba = new Mat();
        Mat mGrey = new Mat();
        MatOfRect faces = new MatOfRect();
        inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mGrey, mGrey);
        face_cascade.detectMultiScale(mGrey, faces);
        long endTime = System.nanoTime();
        // System.out.println(String.format("Detect time: %.2f ms", (float) (endTime - startTime) / 1000000));
        // System.out.println(String.format("Detected %s faces", faces.toArray().length));
        Mat maskResized = new Mat();
        for (Rect rect : faces.toArray()) {
            // Point center = new Point(rect.x + rect.width * 0.5, rect.y + rect.height * 0.5);
            Imgproc.resize(mask, maskResized, new Size(rect.width, rect.height));
            // Imgproc.ellipse(mRgba, center, new Size(rect.width * 0.5, rect.height * 0.5), 0, 0, 360,
            //         new Scalar(255, 0, 255), 4, 8, 0);
            drawTransparency(mRgba, maskResized, rect.x, (int) (rect.y - rect.width / 5));
        }
        return mRgba;
    }
}

public class FaceDetectionWithImageOverlay {

    public static void main(String arg[]) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String window_name = "Capture - Face detection";
        JFrame frame = new JFrame(window_name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        processor my_processor = new processor();
        My_Panel my_panel = new My_Panel();
        frame.setContentPane(my_panel);
        frame.setVisible(true);
        // -- 2. Read the video stream
        Mat webcam_image = new Mat();
        VideoCapture capture = new VideoCapture(0);

        // capture.set(Video., value)
        if (capture.isOpened()) {

            // capture.set(Videoio.CV_CAP_PROP_FRAME_WIDTH, 300);
            // capture.set(Videoio.CV_CAP_PROP_FRAME_HEIGHT, 200);

            while (true) {
                capture.read(webcam_image);

                // Mat resizeimage = new Mat();
                Size sz = new Size(600, 400);
                Imgproc.resize(webcam_image, webcam_image, sz);

                if (!webcam_image.empty()) {
                    frame.setSize(2 * webcam_image.width() + 40, 2 * webcam_image.height() + 60);
                    // -- 3. Apply the classifier to the captured image
                    webcam_image = my_processor.detect(webcam_image);

                    // -- 4. Display the image
                    // my_panel.MatToBufferedImage(webcam_image); // We could look at the error...
                    my_panel.image = Origami.matToBufferedImage(webcam_image);
                    my_panel.repaint();
                } else {
                    System.out.println(" --(!) No captured frame -- Break!");
                    break;
                }
            }
        }
        return;
    }
}