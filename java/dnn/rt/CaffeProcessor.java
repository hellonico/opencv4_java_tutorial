package dnn.rt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CaffeProcessor {
    private final static Logger LOGGER = LoggerFactory.getLogger(CaffeProcessor.class);
    private final List<String> classNames = Files.readAllLines(Paths.get("data/dnn/MobileNetSSD.names"));
    private Net net;

    CaffeProcessor() throws IOException {
//        String proto = "data/dnn/MobileNetSSD_deploy.prototxt";
//        String model = "data/dnn/MobileNetSSD_deploy.caffemodel";
        String proto = "data/dnn/rt/deploy.prototxt";
        String model = "data/dnn/rt/mobilenet_iter_73000.caffemodel";

        this.net = Dnn.readNetFromCaffe(proto, model);
    }

    List<DnnObject> getObjectsInFrame(Mat frame) {

//        int inWidth = 320;
//        int inHeight = 240;
        int inWidth = 300;
        int inHeight = 300;
        double inScaleFactor = 0.007843;
        double thresholdDnn = 0.6;
        double meanVal = 127.5;

        List<DnnObject> objectList = new ArrayList<>();
        int cols = frame.cols();
        int rows = frame.rows();

        Mat blob = Dnn.blobFromImage(frame, inScaleFactor, new Size(inWidth, inHeight),
                new Scalar(meanVal, meanVal, meanVal), false, false);

        net.setInput(blob);
        Mat detections = net.forward();
        System.out.println("F>"+detections.width()+"x"+detections.height()+"..."+detections.total());
//      [0, label, confidence, x-left, y-left, x-right, y-right]
//        [0, 2, 0.98572022, 0.14452386, 0.23069865, 0.76481342, 0.78684169;
//        0, 7, 0.99643564, 0.61877233, 0.13273358, 0.89402419, 0.30446401;
//        0, 12, 0.97574824, 0.18862528, 0.36047107, 0.42626673, 0.9481743]

        detections = detections.reshape(1, (int) detections.total() / 7);
        System.out.println(detections.dump());
        System.out.println("S>"+detections.width()+"x"+detections.height()+"..."+detections.dims());
        // all detected objects
        for (int i = 0; i < detections.rows(); ++i) {
            double confidence = detections.get(i, 2)[0];

            if (confidence < thresholdDnn)
                continue;

            int classId = (int) detections.get(i, 1)[0];

            // calculate position
            int xLeftBottom = (int) (detections.get(i, 3)[0] * cols);
            int yLeftBottom = (int) (detections.get(i, 4)[0] * rows);
            Point leftPosition = new Point(xLeftBottom, yLeftBottom);

            int xRightTop = (int) (detections.get(i, 5)[0] * cols);
            int yRightTop = (int) (detections.get(i, 6)[0] * rows);
            Point rightPosition = new Point(xRightTop, yRightTop);
//
//            float centerX = (xLeftBottom + xRightTop) / 2;
//            float centerY = (yLeftBottom - yRightTop) / 2;
//            Point centerPoint = new Point(centerX, centerY);

            DnnObject dnnObject = new DnnObject();
            dnnObject.objectClassId = classId;
            dnnObject.objectName = classNames.get(classId);
            dnnObject.leftBottom = leftPosition;
            dnnObject.rightTop = rightPosition;
//            dnnObject.centerCoordinate = centerPoint;
            dnnObject.confidence = confidence;
            objectList.add(dnnObject);
        }
        return objectList;
    }

}
