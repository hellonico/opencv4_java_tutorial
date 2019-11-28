package optimizedimageenhance;

import com.isaac.models.ALTMRetinex;
import com.isaac.utils.ImShow;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

public class ALTMRetinexExample {

    // Local Adaptation Parameters
    private static final int r = 10;
    private static final double eps = 0.01;
    private static final double eta = 36.0;
    private static final double lambda = 10.0;
    private static final double krnlRatio = 0.1;

    public static void main (String[] args) {
        Origami.init();
        String imgPath = "data/marcel2019.jpg";
        Mat image = Imgcodecs.imread(imgPath, Imgcodecs.IMREAD_REDUCED_COLOR_8);
        new ImShow("Original").showImage(image);
        Mat result = ALTMRetinex.enhance(image, r, eps, eta, lambda, krnlRatio);
        new ImShow("result").showImage(result);
    }
}
