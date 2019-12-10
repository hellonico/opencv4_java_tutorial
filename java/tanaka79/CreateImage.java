package tanaka79;

import org.opencv.core.Mat;
import origami.Origami;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;

public class CreateImage {
    public static void main(String[] args) {
        Origami.init();
        Mat im = imread("data/marcel2019.jpg");    // 入力画像の取得
        imwrite("data/lupin3.jpeg", im);                // 画像をJPG形式で保存
    }
}
