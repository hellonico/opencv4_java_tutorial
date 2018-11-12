package dnn.rt;

import java.io.IOException;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

public class OnePicture {
    private static String label(DnnObject obj){
        return String.format("%s [%.0f%%] ", obj.objectName, obj.confidence*100);
    }

    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        CaffeProcessor processor = new CaffeProcessor();
        Mat frame = Imgcodecs.imread("data/dnnmodel/dog.jpg");
        List<DnnObject> detectObject = processor.getObjectsInFrame(frame);
        for (DnnObject obj : detectObject) {
            Imgproc.rectangle(frame, obj.leftBottom, obj.rightTop, new Scalar(255, 0, 0), 1);
            Imgproc.putText(frame, label(obj), obj.leftBottom, Imgproc.FONT_HERSHEY_PLAIN, 1, new Scalar(255, 0, 0));
        }
        Imgcodecs.imwrite("DetectedObject.jpg", frame);
    }
}
