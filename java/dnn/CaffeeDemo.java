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

        String sourceImageFile = "data/caffee/jeunehomme.jpg";
        ageDemo(sourceImageFile);
        genderDemo(sourceImageFile);

        ageDemo("data/caffee/teenager2.jpg");
        genderDemo("data/caffee/teenager2.jpg");
    }

    private static void ageDemo(String sourceImageFile) {

        String tfnetFile = "data/caffee/age_net.caffemodel";
        String protoFil = "data/caffee/age.prototxt";
        List labels =
        Arrays.asList("(0, 2)","(4, 6)","(8, 12)","(15, 20)","(25, 32)","(38, 43)","(48, 53)","(60, 100)");
        runCaffeeNetwork(sourceImageFile, tfnetFile, protoFil, labels);
    }

    private static void genderDemo(String sourceImageFile) {
        String tfnetFile = "data/caffee/gender_net.caffemodel";
        String protoFil = "data/caffee/gender.prototxt";
        List labels = Arrays.asList("Male", "Female");

        runCaffeeNetwork(sourceImageFile, tfnetFile, protoFil, labels);
    }

    private static void runCaffeeNetwork(String sourceImageFile, String tfnetFile, String protoFil, List labels) {
        Net net = Dnn.readNetFromCaffe(protoFil, tfnetFile);
        List<String> layernames = net.getLayerNames();

        Mat image = Imgcodecs.imread(sourceImageFile);
//        Mat inputBlob = Dnn.blobFromImage(image, 1.0, new Size(256, 256), new Scalar(0), true, true);
        Mat inputBlob = Dnn.blobFromImage(image, 1.0, new Size(256, 256));
        net.setInput(inputBlob);
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        Mat result = net.forward();
        result = result.reshape(1,1);

        System.out.println(result.dump());
        Core.MinMaxLocResult minmax = Core.minMaxLoc(result);
        System.out.println(labels.get((int) minmax.maxLoc.x ));
    }

}
