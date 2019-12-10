package tanaka79.image;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

public class Trimming {
    public static void main(String[] args) {

        Origami.init();
        // 入力画像の取得
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");
        Rect roi = new Rect(280, 60, 120, 100);
        Mat im2 = new Mat(im, roi);
        // 結果を保存
        Imgcodecs.imwrite("anime.png", im2);
    }
}
