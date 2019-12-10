package tanaka79.image;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class Canny {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");            // 入力画像の取得
        Mat gray = new Mat();
        Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY);    // グレースケール変換
        Imgproc.Canny(gray, gray, 400, 500, 5, true);        // Cannyアルゴリズムで輪郭検出
        Imgcodecs.imwrite("tanaka.jpg", gray);            // エッジ画像の出力
    }
}
