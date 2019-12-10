package tanaka79;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class MedianBlur {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");    // 入力画像の取得
        Mat dst = new Mat();
        Imgproc.medianBlur(im, dst, 5);
        Imgcodecs.imwrite("tanaka.jpg", dst);        // 出力画像の保存
    }
}
