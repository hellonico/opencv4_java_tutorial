package tanaka79.image;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;


public class EqualizeHistgram {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");            // 入力画像の取得
        Mat gray = new Mat();
        Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY);    // 画像のグレースケール変換
        Imgproc.equalizeHist(gray, gray);            // グレースケール画像のヒストグラムを平坦化
        Imgcodecs.imwrite("tanaka.jpg", gray);            // 画像の出力
    }
}
