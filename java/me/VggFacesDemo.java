package me;

import org.opencv.core.*;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgproc.Imgproc;
import origami.ImShow;
import origami.Origami;

import java.io.File;
import java.util.List;

import static org.opencv.dnn.Dnn.blobFromImage;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class VggFacesDemo {

    public static void main(String[] args) throws Exception {
        Origami.init();
        List netSpec = origami.Dnn.readNetFromSpec("networks.caffe:vgg-faces:1.0.0");
        List<String> labels = (List<String>) netSpec.get(2);
        String path = ((File) netSpec.get(3)).getAbsolutePath();
        runCaffeeNetwork((Net) netSpec.get(0), path+"/Daniel.jpg", labels);
    }

    private static void runCaffeeNetwork(Net net, String sourceImageFile, List<String> labels) {
        Mat image = imread(sourceImageFile);
        Mat inputBlob = blobFromImage(image, 1.0, new Size(224, 224), new Scalar(129.1863,104.7624,93.5940), false);
        net.setInput(inputBlob);
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        Mat result = net.forward("prob");
        result = result.reshape(1, 1);
        Core.MinMaxLocResult minmax = Core.minMaxLoc(result);
        String name = labels.get((int) minmax.maxLoc.x);
        Imgproc.putText(image,name, new Point(50,50),Imgproc.FONT_HERSHEY_PLAIN, 3.0, new Scalar(255,255,255),2);
        ImShow.show(image);
    }

}
