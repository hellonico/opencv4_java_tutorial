package subh123;

import org.opencv.core.*;
import org.opencv.dnn.*;
import org.opencv.utils.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.scijava.nativelib.NativeLoader;
import origami.Origami;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Yolo {

    private static List<String> getOutputNames(Net net) {
        List<String> names = new ArrayList<>();
        List<Integer> outLayers = net.getUnconnectedOutLayers().toList();
        List<String> layersNames = net.getLayerNames();
        // unfold and create R-CNN layers from the loaded YOLO model//
        outLayers.forEach((item) -> names.add(layersNames.get(item - 1)));
        return names;
    }

    public static void main(String[] args) throws Exception {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        // Download and load only wights for YOLO , this is obtained from
        // official YOLO site//
        // Download and load cfg file for YOLO , can be obtained from
        // official site//

        // String modelWeights = "data/yolov3/yolov3.weights";
        // String modelConfiguration = "data/yolov3/yolov3.cfg";

        String modelWeights = "data/yolo/yolov2-tiny.weights";
        String modelConfiguration = "data/yolo/yolov2-tiny.cfg";
        
        // My video file to be analysed//
        // String filePath = "data/cars.mpeg";
        String filePath = "data/cars.mpeg";

        // Load video using the videocapture method//
        VideoCapture cap = new VideoCapture(filePath);
        // define a matrix to extract and store pixel info from video//
        Mat frame = new Mat();

        // the lines below create a frame to display the resultant video with
        // object detection and localization//
        JFrame jframe = new JFrame("Video");
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setSize(600, 600);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // OpenCV DNN supports models trained from
        // various frameworks like Caffe and
        // TensorFlow. It also supports various
        // networks architectures based on YOLO//
        Net net = Dnn.readNetFromDarknet(modelConfiguration, modelWeights);
        List<String> outBlobNames = getOutputNames(net);

        while (true) {
            if (cap.read(frame)) {
                findShapes(frame, net, outBlobNames);
                ImageIcon image = new ImageIcon(Origami.matToBufferedImage(frame));
                // setting the results into a frame
                // and
                // initializing it //
                vidpanel.setIcon(image);
                vidpanel.repaint();
            }
        }
    }

    final static Size sz = new Size(416, 416);

    private static void findShapes(Mat frame, Net net, List<String> outBlobNames) {
        List<Mat> result = new ArrayList<>();

        // We feed one frame of
        // video into the network
        // at a time, we have to
        // convert the image to a
        // blob. A blob is a
        // pre-processed image
        // that serves as the
        // input.//
        Mat blob = Dnn.blobFromImage(frame, 0.00392, sz, new Scalar(0), true, false);
        net.setInput(blob);
        net.forward(result, outBlobNames); // Feed forward the model to get output //

        // outBlobNames.forEach(System.out::println);
        // result.forEach(System.out::println);

        float confThreshold = 0.6f; // Insert thresholding beyond which the model will detect objects//
        List<Integer> clsIds = new ArrayList<>();
        List<Float> confs = new ArrayList<>();
        List<Rect> rects = new ArrayList<>();
        for (int i = 0; i < result.size(); ++i) {
            // each row is a candidate detection, the 1st 4 numbers are
            // [center_x, center_y, width, height], followed by (N-4) class probabilities
            Mat level = result.get(i);
            for (int j = 0; j < level.rows(); ++j) {
                Mat row = level.row(j);
                Mat scores = row.colRange(5, level.cols());
                Core.MinMaxLocResult mm = Core.minMaxLoc(scores);
                float confidence = (float) mm.maxVal;
                Point classIdPoint = mm.maxLoc;
                if (confidence > confThreshold) {
                    int centerX = (int) (row.get(0, 0)[0] * frame.cols()); // scaling for drawing the bounding
                                                                           // boxes//
                    int centerY = (int) (row.get(0, 1)[0] * frame.rows());
                    int width = (int) (row.get(0, 2)[0] * frame.cols());
                    int height = (int) (row.get(0, 3)[0] * frame.rows());
                    int left = centerX - width / 2;
                    int top = centerY - height / 2;

                    clsIds.add((int) classIdPoint.x);
                    confs.add((float) confidence);
                    rects.add(new Rect(left, top, width, height));
                }
            }
        }
        float nmsThresh = 0.5f;
        MatOfFloat confidences = new MatOfFloat(Converters.vector_float_to_Mat(confs));
        Rect[] boxesArray = rects.toArray(new Rect[0]);
        MatOfRect boxes = new MatOfRect(boxesArray);
        MatOfInt indices = new MatOfInt();
        // We draw the bounding boxes for
        // objects here//
        Dnn.NMSBoxes(boxes, confidences, confThreshold, nmsThresh, indices);

        int[] ind = indices.toArray();
        int j = 0;
        for (int i = 0; i < ind.length; ++i) {
            int idx = ind[i];
            Rect box = boxesArray[idx];
            Imgproc.rectangle(frame, box.tl(), box.br(), new Scalar(0, 0, 255), 2);
        }
    }

}
