package me;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import origami.Origami;
import origami.utils.Downloader;

public class BodyClassifier {
    static final String DEFAULT_CLASSIFIER = "https://raw.githubusercontent.com/opencv/opencv/master/data/haarcascades/haarcascade_upperbody.xml";
    static final String DEFAULT_IMAGE = "https://www.netclipart.com/pp/m/106-1066497_suit-man-png-man-in-suit-png.png";
    static final String IMAGE_PATH = "image.jpg";
    static final String CLASSIFIER_PATH = "haarcascade.xml";

    public static void main(String[] args) throws Exception {
        Origami.init();

        String imageUrl = args.length >= 1 && args[0]!=null ? args[0] : DEFAULT_IMAGE;
        String classifierUrl = args.length >= 2 && args[1]!=null ? args[1] : DEFAULT_CLASSIFIER;

        Downloader.transfer(imageUrl, IMAGE_PATH);
        Downloader.transfer(classifierUrl, CLASSIFIER_PATH);

        CascadeClassifier classifier = new CascadeClassifier();
        classifier.load(CLASSIFIER_PATH);
        Mat mat = imread(IMAGE_PATH);
        MatOfRect bodies = new MatOfRect();
        classifier.detectMultiScale(mat, bodies);

        for (Rect body : bodies.toList()) {
            Imgproc.rectangle(mat, new Point(body.x, body.y), new Point(body.x + body.width, body.y + body.height),
                    new Scalar(0, 100, 0), 3);
        }
        imwrite("output.jpg", mat);

    }


}
