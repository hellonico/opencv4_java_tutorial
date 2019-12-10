package tanaka79.image;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.photo.Photo;
import origami.Origami;

public class FastNlMeans {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");                    // 入力画像の取得
        Photo.fastNlMeansDenoising(im, im);
        Imgcodecs.imwrite("tanaka.jpg", im);                // 画像の出力
    }
}
