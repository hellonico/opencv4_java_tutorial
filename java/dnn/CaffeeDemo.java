package dnn;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.scijava.nativelib.NativeLoader;

import java.util.Arrays;
import java.util.List;

public class CaffeeDemo {

    public static void main(String[] args) throws Exception {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        genderDemo();

    }

    private static void genderDemo() {
        String sourceImageFile = "data/caffee/jeunehomme.jpg";
        String tfnetFile = "data/caffee/gender_net.caffemodel";
        String protoFil = "data/caffee/gender.prototxt";
        List labels = Arrays.asList("Male", "Female");

        runCaffeeNetwork(sourceImageFile, tfnetFile, protoFil, labels);
    }

    private static void runCaffeeNetwork(String sourceImageFile, String tfnetFile, String protoFil, List labels) {
        Net net = Dnn.readNetFromCaffe(protoFil, tfnetFile);
        List<String> layernames = net.getLayerNames();
        System.out.println(layernames);

        Mat image = Imgcodecs.imread(sourceImageFile);
        Mat inputBlob = Dnn.blobFromImage(image, 1.0, new Size(256, 256), new Scalar(0), true, true);
        net.setInput(inputBlob);
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        Mat result = net.forward();

        System.out.println(result.dump());

        Core.MinMaxLocResult minmax = Core.minMaxLoc(result);

        System.out.println(labels.get((int) minmax.maxLoc.x ));
    }

}
