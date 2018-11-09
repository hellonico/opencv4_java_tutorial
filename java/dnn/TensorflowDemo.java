package dnn;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import org.scijava.nativelib.NativeLoader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class TensorflowDemo {


    private static void normAssert(Mat ref, Mat test) {
        double normL1 = Core.norm(ref, test, Core.NORM_L1) / ref.total();
        double normLInf = Core.norm(ref, test, Core.NORM_INF) / ref.total();
        System.out.println(normL1);
        System.out.println(normLInf);
    }


    public static void main(String[] args) throws Exception {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        String sourceImageFile = "data/tf/grace_hopper_227.png";
        String tfnetFile = "data/tf/tensorflow_inception_graph.pb";

        Net net = Dnn.readNetFromTensorflow(tfnetFile);
        Mat image = Imgcodecs.imread(sourceImageFile);
        Mat inputBlob = Dnn.blobFromImage(image, 1.0, new Size(224, 224), new Scalar(0), true, true);
        net.setInput(inputBlob, "input");
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);

        List<String> layernames = net.getLayerNames();
//        System.out.println(layernames);

        Mat result = net.forward("softmax2");

        result = result.reshape(1, 1);
        Core.MinMaxLocResult minmax = Core.minMaxLoc(result);
//        System.out.println(minmax.maxLoc.toString());
//        System.out.println(result.get(0, 866)[0]);

        List<String> labels =
                Files.readAllLines(Paths.get("data/tf/imagenet_slim_labels.txt"));
        System.out.println(labels.get((int) minmax.maxLoc.x ));

        // check scores
        Mat top5RefScores =
                new MatOfFloat(0.63032645f, 0.2561979f, 0.032181446f, 0.015721032f, 0.014785315f).reshape(1, 1);

        Core.sort(result, result, Core.SORT_DESCENDING);
        normAssert(result.colRange(0, 5), top5RefScores);

    }

}
