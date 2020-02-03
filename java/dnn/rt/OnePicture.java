package dnn.rt;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.io.IOException;
import java.util.List;

public class OnePicture {
    private static String label(DnnObject obj) {
        return String.format("%s [%.0f%%] ", obj.objectName, obj.confidence * 100);
    }

    public static void main(String[] args) throws IOException {
        Origami.init();
        CaffeProcessor processor = new CaffeProcessor();
        Mat frame = Imgcodecs.imread("data/dnn/rt/dog.jpg");
        List<DnnObject> detectObject = processor.getObjectsInFrame(frame);

        for (DnnObject obj : detectObject) {
            Imgproc.rectangle(frame, obj.leftBottom, obj.rightTop, new Scalar(255, 0, 0), 1);
            Imgproc.putText(frame, label(obj), obj.leftBottom, Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(255, 0, 0));
        }
        Imgcodecs.imwrite("detectedobject.jpg", frame);
    }
}
