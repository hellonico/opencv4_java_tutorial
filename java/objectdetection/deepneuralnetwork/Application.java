package objectdetection.deepneuralnetwork;

import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

    public static void main(String[] args) throws IOException {
        Origami.init();
        DeepNeuralNetworkProcessor processor = new DeepNeuralNetworkProcessor();
//        System.loadLibrary("opencv_java340");
        List<DnnObject> detectObject = new ArrayList<>();
        VideoCapture capturre = new VideoCapture(0);
        while (true)
        {
           Mat frame = new Mat();
           capturre.read(frame);
           detectObject = processor.getObjectsInFrame(frame, false);
           for (DnnObject obj: detectObject)
           {
               Imgproc.rectangle(frame,obj.getLeftBottom(),obj.getRightTop(),new Scalar(255,0,0),1);
           }
           Imgcodecs.imwrite("DetectedObject.jpg",frame);
        }

    }
}
