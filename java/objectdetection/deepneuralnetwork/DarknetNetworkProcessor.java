package objectdetection.deepneuralnetwork;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DeepNeuralNetworkProcessor {
    private final static Logger LOGGER = LoggerFactory.getLogger(DeepNeuralNetworkProcessor.class);
    private Net net;
//    private final String proto = "data/dnnmodel/MobileNetSSD_deploy.prototxt";
//    private final String model = "data/dnnmodel/MobileNetSSD_deploy.caffemodel";

    private final String proto = "data/dnn/darknetd/yolov3.cfg";
    private final String model = "data/dnn/darknetd/yolov3.weights";
//
//    private final String[] classNames = {"background",
//            "aeroplane", "bicycle", "bird", "boat",
//            "bottle", "bus", "car", "cat", "chair",
//            "cow", "diningtable", "dog", "horse",
//            "motorbike", "person", "pottedplant",
//            "sheep", "sofa", "train", "tvmonitor"};
    private final List<String>  classNames =
        Files.readAllLines(Paths.get("data/tf/imagenet_slim_labels.txt"));


    public DeepNeuralNetworkProcessor() throws IOException {
      this.net = Dnn.readNetFromDarknet(proto, model);
    }


    public int getObjectCount(Mat frame, boolean isGrayFrame, String objectName) {

//        int inWidth = 320;
//        int inHeight = 240;
        int inWidth = 608;
        int inHeight = 608;

        double inScaleFactor = 0.007843;
        double thresholdDnn =  0.2;
        double meanVal = 127.5;

        int personObjectCount = 0;
        Mat blob = null;
        Mat detections = null;


        try {
            if (isGrayFrame)
                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_GRAY2RGB);

            blob = Dnn.blobFromImage(frame, inScaleFactor,
                    new Size(inWidth, inHeight),
                    new Scalar(meanVal, meanVal, meanVal),
                    false, false);
            net.setInput(blob);
            detections = net.forward();
            detections = detections.reshape(1, (int) detections.total() / 7);
            for (int i = 0; i < detections.rows(); ++i) {
                double confidence = detections.get(i, 2)[0];

                if (confidence < thresholdDnn)
                    continue;

                int classId = (int) detections.get(i, 1)[0];
                if (classNames.get(classId).toString() != objectName.toLowerCase()) {
                    continue;
                }
                personObjectCount++;
            }
        } catch (Exception ex) {
            LOGGER.error("An error occurred DNN: ", ex);
        }
        return personObjectCount;
    }

    public List<DnnObject> getObjectsInFrame(Mat frame, boolean isGrayFrame) {

        int inWidth = 608;
        int inHeight = 608;
        double inScaleFactor = 0.007843;
        double thresholdDnn =  0.2;
        double meanVal = 127.5;
        
        Mat blob = null;
        Mat detections = null;
        List<DnnObject> objectList = new ArrayList<>();

        int cols = frame.cols();
        int rows = frame.rows();

        try {
            if (isGrayFrame)
                Imgproc.cvtColor(frame, frame, Imgproc.COLOR_GRAY2RGB);

            blob = Dnn.blobFromImage(frame, inScaleFactor,
                    new Size(inWidth, inHeight),
                    new Scalar(meanVal, meanVal, meanVal),
                    false, false);

            net.setInput(blob);
            detections = net.forward();
            // get the sample slice from 3D matrix (batch, box, classes+5)
//            detections = detections.reshape(1, (int) detections.total() / 7);

            //all detected objects
            for (int i = 0; i < detections.rows(); ++i) {
                double confidence = detections.get(i, 2)[0];

                if (confidence < thresholdDnn)
                    continue;

                int classId = (int) detections.get(i, 1)[0];

                //calculate position
                int xLeftBottom = (int) (detections.get(i, 3)[0] * cols);
                int yLeftBottom = (int) (detections.get(i, 4)[0] * rows);
                Point leftPosition = new Point(xLeftBottom, yLeftBottom);

                int xRightTop = (int) (detections.get(i, 5)[0] * cols);
                int yRightTop = (int) (detections.get(i, 6)[0] * rows);
                Point rightPosition = new Point(xRightTop, yRightTop);

                float centerX = (xLeftBottom + xRightTop) / 2;
                float centerY = (yLeftBottom - yRightTop) / 2;
                Point centerPoint = new Point(centerX, centerY);


                DnnObject dnnObject = new DnnObject(classId, classNames.get(classId), leftPosition, rightPosition, centerPoint);
                objectList.add(dnnObject);
            }

        } catch (Exception ex) {
            LOGGER.error("An error occurred DNN: ", ex);
        }
        return objectList;
    }


}
