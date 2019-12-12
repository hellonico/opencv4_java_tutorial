package dnn;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class YoloV3Demo {

    public static void main(String[] args) throws Exception {
        Origami.init();
        runDark(new String[] { "data/yolov3/catwalk.jpg", "data/yolov3/bird.jpg" });
    }

    private static void runDark(String[] sourceImageFile) throws IOException {
        String tfnetFile = "data/yolov3/yolov3.weights";
        String protoFil = "data/yolov3/yolov3.cfg";
        List<String> labels = Files.readAllLines(Paths.get("data/yolov3/coco.names"));
        Net net = Dnn.readNetFromDarknet(protoFil, tfnetFile);
        List<String> layers = getOutputsNames(net);
        for (String image : sourceImageFile) {
            runInference(net, layers, labels, image);
        }
    }

    // Get the names of the output layers
    private static List<String> getOutputsNames(Net net) {
        List<String> layersNames = net.getLayerNames();
        // System.out.println(net.getUnconnectedOutLayers().dump());
        return net.getUnconnectedOutLayers().toList().stream().map(i -> i - 1).map(layersNames::get)
                .collect(Collectors.toList());
    }

    // Given a file from assets, run inference
    private static void runInference(Net net, List<String> layers, List<String> labels, String filename) {
        final int IN_WIDTH = 416;
        final int IN_HEIGHT = 416;
        final double IN_SCALE_FACTOR = 0.00392157;
        final int MAX_RESULTS = 20;

        Mat frame = Imgcodecs.imread(filename, Imgcodecs.IMREAD_COLOR);

        // Forward image through network.
        Mat blob = Dnn.blobFromImage(frame, IN_SCALE_FACTOR, new Size(IN_WIDTH, IN_HEIGHT), new Scalar(0, 0, 0), false);
        net.setInput(blob);

        List<Mat> outputs = new ArrayList<>();
        for (int i = 0; i < layers.size(); i++)
            outputs.add(new Mat());

        net.forward(outputs, layers);

        List<Integer> labelIDs = new ArrayList<>();
        List<Float> probabilities = new ArrayList<>();
        List locations = new ArrayList<>();

        postprocess(filename, frame, labels, outputs, labelIDs, probabilities, locations, MAX_RESULTS);
    }

    private static void postprocess(String filename, Mat frame, List<String> labels, List<Mat> outs,
            List<Integer> classIds, List<Float> confidences, List locations, int nResults) {
        List<Rect> tmpLocations = new ArrayList<>();
        List<Integer> tmpClasses = new ArrayList<>();
        List<Float> tmpConfidences = new ArrayList<>();
        int w = frame.width();
        int h = frame.height();

        for (Mat out : outs) {
            // Scan through all the bounding boxes output from the network and keep only the
            // ones with high confidence scores. Assign the box's class label as the class
            // with the highest score for the box.
            final float[] data = new float[(int) out.total()];
            out.get(0, 0, data);

            int k = 0;
            for (int j = 0; j < out.height(); j++) {

                // Each row of data has 4 values for location, followed by N confidence values
                // which correspond to the labels
                Mat scores = out.row(j).colRange(5, out.width());
                // Get the value and location of the maximum score
                Core.MinMaxLocResult result = Core.minMaxLoc(scores);
                if (result.maxVal > 0) {
                    float center_x = data[k + 0] * w;
                    float center_y = data[k + 1] * h;
                    float width = data[k + 2] * w;
                    float height = data[k + 3] * h;
                    float left = center_x - width / 2;
                    float top = center_y - height / 2;

                    tmpClasses.add((int) result.maxLoc.x);
                    tmpConfidences.add((float) result.maxVal);
                    tmpLocations.add(new Rect((int) left, (int) top, (int) width, (int) height));

                }
                k += out.width();
            }
        }
        annotateFrame(frame, labels, classIds, confidences, nResults, tmpLocations, tmpClasses, tmpConfidences);
        Imgcodecs.imwrite("out/" + new File(filename).getName(), frame);
    }

    private static void annotateFrame(Mat frame, List<String> labels, List<Integer> classIds, List<Float> confidences,
            int nResults, List<Rect> tmpLocations, List<Integer> tmpClasses, List<Float> tmpConfidences) {
        // Perform non maximum suppression to eliminate redundant overlapping boxes with
        // lower confidences and sort by confidence

        // many overlapping results coming from yolo so have to use it
        MatOfRect locMat = new MatOfRect();
        locMat.fromList(tmpLocations);
        MatOfFloat confidenceMat = new MatOfFloat();
        confidenceMat.fromList(tmpConfidences);
        MatOfInt indexMat = new MatOfInt();
        Dnn.NMSBoxes(locMat, confidenceMat, 0.1f, 0.1f, indexMat);
        // StringBuilder output = new StringBuilder();

        for (int i = 0; i < indexMat.total() && i < nResults; ++i) {
            int idx = (int) indexMat.get(i, 0)[0];
            classIds.add(tmpClasses.get(idx));
            confidences.add(tmpConfidences.get(idx));
            Rect box = tmpLocations.get(idx);
            String label = labels.get(classIds.get(i));
            Imgproc.rectangle(frame, box, new Scalar(0, 0, 0), 2);
            Imgproc.putText(frame, label, new Point(box.x, box.y), Imgproc.FONT_HERSHEY_PLAIN, 1.0, new Scalar(0, 0, 0),
                    2);

        }
    }
}
