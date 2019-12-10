package tanaka79;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class Resize {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");    // 入力画像の取得
        Mat im2 = new Mat();
        Mat im3 = new Mat();
        Size sz = im.size();
        Imgproc.resize(im, im2, new Size(sz.width * 2, sz.height * 2));                // 2倍拡大
        Imgproc.resize(im, im3, new Size(sz.width * 0.5, sz.height * 0.5));            // 1/2倍に縮小
        Imgcodecs.imwrite("tanaka.jpg", im2);            // 出力画像の保存
        Imgcodecs.imwrite("test3.jpg", im3);            // 出力画像の保存
    }
}
