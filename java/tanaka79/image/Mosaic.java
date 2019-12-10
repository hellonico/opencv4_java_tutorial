package tanaka79.image;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class Mosaic {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");                    // 入力画像の取得
        Imgproc.resize(im, im, new Size(), 0.1, 0.1, Imgproc.INTER_NEAREST);    // 画像サイズを1/10倍
        Imgproc.resize(im, im, new Size(), 10.0, 10.0, Imgproc.INTER_NEAREST);    // 画像サイズを10倍
        Imgcodecs.imwrite("tanaka.jpg", im);                    // 画像の出力
    }
}
