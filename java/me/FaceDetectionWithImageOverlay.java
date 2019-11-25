package me;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.scijava.nativelib.NativeLoader;
import picocli.CommandLine;

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
    private Mat mask;
    private double adjust;

    // Create a constructor method
    public processor(String _mask, double _adjust) {
        mask = Imgcodecs.imread(_mask, Imgcodecs.IMREAD_UNCHANGED);
        adjust = _adjust;

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
        Mat submat = frame.submat(yPos, yPos + transp.rows(), xPos, xPos + transp.cols());
        transp.copyTo(submat, mask);
    }

    public void detect(Mat inputframe) {
        long startTime = System.nanoTime();
        // Mat mRgba = new Mat();
        Mat mGrey = new Mat();
        MatOfRect faces = new MatOfRect();
        // inputframe.copyTo(mRgba);
        inputframe.copyTo(mGrey);
        Imgproc.cvtColor(inputframe, mGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mGrey, mGrey);
        face_cascade.detectMultiScale(mGrey, faces);
        long endTime = System.nanoTime();
        Mat maskResized = new Mat();
        for (Rect rect : faces.toArray()) {
            Imgproc.resize(mask, maskResized, new Size(rect.width, rect.height));
            int adjusty = (int) (rect.y - rect.width * adjust);
            try {
                drawTransparency(inputframe, maskResized, rect.x, adjusty);
            } catch(Exception e) {

            }
        }
    }
}

@CommandLine.Command(name = "facedetection", version="1.0.0", mixinStandardHelpOptions = true, description = "Turn a picture into sepia")
public class FaceDetectionWithImageOverlay implements Callable<Integer> {

    @CommandLine.Option(names = {"-m", "--mask"}, description = "Overlay mask", required = false)
    private String _mask = "data/masquerade_mask.png";

    @CommandLine.Option(names = {"-a", "--adjust"}, description = "Overlay mask", required = false)
    Double adjust = 0.2;

    @CommandLine.Option(names = {"-c", "--camera"}, description = "Camera Id", required = false)
    Integer camIndex = 0;

    public FaceDetectionWithImageOverlay() {
        Core.setNumThreads(4);
    }


    public Integer call() {
        String window_name = "Capture - Face detection";
        JFrame frame = new JFrame(window_name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        processor my_processor = new processor(_mask, adjust);
        My_Panel my_panel = new My_Panel();
        frame.setContentPane(my_panel);
        frame.setVisible(true);
        // -- 2. Read the video stream
        Mat webcam_image = new Mat();
        VideoCapture capture = new VideoCapture(0);

        // capture.set(Video., value)
        if (capture.isOpened()) {

            capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 600);
            capture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 800);

            while (capture.read(webcam_image)) {

                // Mat resizeimage = new Mat();
                Size sz = new Size(600, 400);
                Imgproc.resize(webcam_image, webcam_image, sz);

                frame.setSize(2 * webcam_image.width() + 40, 2 * webcam_image.height() + 60);

                my_processor.detect(webcam_image);
                my_panel.image = (BufferedImage) HighGui.toBufferedImage(webcam_image);
                my_panel.repaint();
            }
        }
        return 0;
    }

    public static void main(String args[]) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    }
}