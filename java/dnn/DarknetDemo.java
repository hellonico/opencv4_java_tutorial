package dnn;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class DarknetDemo {

    public static void main(String[] args) throws Exception {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        genderDemo();

    }

    private static void genderDemo() throws IOException {
//        String sourceImageFile = "data/dnn/darknetd/eagle.jpg";

        runDark("data/dnn/darknetd/eagle.jpg");
        runDark("data/dnn/darknetd/dog.jpg");
        runDark("data/dnn/darknetd/horses.jpg");
    }

    private static void runDark(String sourceImageFile) throws IOException {
        String tfnetFile = "data/dnn/darknetd/yolov3.weights";
        String protoFil = "data/dnn/darknetd/yolov3.cfg";
        List<String> labels =
                Files.readAllLines(Paths.get("data/dnn/darknetd/labels.txt"));

        runDarknetNetwork(sourceImageFile, tfnetFile, protoFil, labels);
    }

    private static void runDarknetNetwork(String sourceImageFile, String tfnetFile, String protoFil, List labels) {
        Net net = Dnn.readNetFromDarknet(protoFil, tfnetFile);

        List<String> layernames = net.getLayerNames();
        System.out.println(layernames);

        Mat image = Imgcodecs.imread(sourceImageFile);
        Mat inputBlob = Dnn.blobFromImage(image, 1.0, new Size(608, 608), new Scalar(0), true ,true);
        net.setInput(inputBlob);
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        Mat result = net.forward();
        result = result.reshape( 1);
        System.out.println(result.width());
        Core.MinMaxLocResult minmax = Core.minMaxLoc(result);
        System.out.println(minmax.maxLoc);
        System.out.println(minmax.maxVal);

        System.out.println(labels.get((int) minmax.maxLoc.x ));
        System.out.println(labels.get((int) minmax.maxLoc.x -1 ));
        System.out.println(labels.get((int) minmax.maxLoc.x +1));
    }

}
