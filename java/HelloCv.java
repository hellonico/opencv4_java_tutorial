import org.opencv.core.CvType;
import org.opencv.core.Mat;
import origami.Origami;

public class HelloCv {
    public static void main(String[] args) throws Exception {
        Origami.init();
        Mat hello = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println(hello.dump());
    }
}
