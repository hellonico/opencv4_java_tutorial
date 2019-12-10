package tanaka79;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class DrawLine {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");    // 入力画像の取得
        // 直線描画
        Imgproc.line(im, new Point(0, 0), new Point(100, 100), new Scalar(0, 0, 200), 10);
        Imgcodecs.imwrite("tanaka.jpg", im);            // 出力画像の保存
    }
}
