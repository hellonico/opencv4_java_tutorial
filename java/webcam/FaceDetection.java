package webcam;

/*  
 * Captures the camera stream with OpenCV  
 * Search for the faces  
 * Display a circle around the faces using Java
 * https://udallascs.wordpress.com/2014/04/01/face-detection-with-a-webcam-using-only-opencv-with-java-and-not-javacv/
 */

import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
        for (Rect rect : faces.toArray()) {
            Point center = new Point(rect.x + rect.width * 0.5, rect.y + rect.height * 0.5);
            Imgproc.ellipse(mRgba, center, new Size(rect.width * 0.5, rect.height * 0.5), 0, 0, 360,
                    new Scalar(255, 0, 255), 4, 8, 0);

        }
        return mRgba;
    }
}

