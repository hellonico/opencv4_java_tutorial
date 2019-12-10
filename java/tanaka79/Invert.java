package tanaka79;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

public class Invert {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");    // 入力画像の取得
        Core.bitwise_not(im, im);                // 色反転(Not演算)
        Imgcodecs.imwrite("tanaka.jpg", im);            // 出力画像の保存
    }
}
