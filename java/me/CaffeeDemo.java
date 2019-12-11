package me;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.Files.*;

public class CaffeeDemo {

    public static void main(String[] args) throws Exception {
        Origami.init();

        facesDemo("data/caffee/vgg_faces/ak.png");
        facesDemo("data/caffee/vgg_faces/craig-bierko-87701.jpg");
        facesDemo("data/caffee/vgg_faces/callie_thorne.jpg");
        facesDemo("data/caffee/vgg_faces/640px-Dan_Feuerriegel_2014.jpg");
        facesDemo("data/caffee/vgg_faces/Daniel.jpg");

//        // adam brody 笑
//        facesDemo("data/caffee/vgg_faces/Jeff-Bezos-2017.jpg");
//        facesDemo("data/caffee/vgg_faces/jeff2.jpg");

        // FAIL
//        facesDemo("data/caffee/vgg_faces/1280_adam_brody_getty.jpg");


    }

    private static void facesDemo(String sourceImageFile) throws IOException {
        String protoFile = "data/caffee/vgg_faces/VGG_FACE_deploy.prototxt";
        String modelFiles = "data/caffee/vgg_faces/VGG_FACE.caffemodel";
        List<String> labels = readAllLines(Paths.get("data/caffee/vgg_faces/names.txt"));
        runCaffeeNetwork(sourceImageFile, modelFiles, protoFile, labels);
    }

    private static void runCaffeeNetwork(String sourceImageFile, String tfnetFile, String protoFil, List<String> labels) {
        Net net = Dnn.readNetFromCaffe(protoFil, tfnetFile);
        Mat image = Imgcodecs.imread(sourceImageFile);
        Mat inputBlob = Dnn.blobFromImage(image, 1.0, new Size(224, 224), new Scalar(129.1863,104.7624,93.5940), false);
        net.setInput(inputBlob);
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        Mat result = net.forward("prob");
        result = result.reshape(1, 1);

        System.out.println(result.dump());
        Core.MinMaxLocResult minmax = Core.minMaxLoc(result);
        System.out.println(labels.get((int) minmax.maxLoc.x));
    }

}
