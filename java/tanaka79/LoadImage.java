package tanaka79;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

public class LoadImage {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/marcel2019.jpg");    // 入力画像の取得
        Imgcodecs.imwrite("data/lupin3.jpeg", im);            // 画像データをJPG形式で保存
    }
}
