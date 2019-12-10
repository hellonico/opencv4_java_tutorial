package tanaka79.image;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class DetectHSV {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");                                            // 入力画像の取得
        Mat hsv = new Mat();
        Mat mask = new Mat();
        Mat im2 = new Mat();
        Imgproc.cvtColor(im, hsv, Imgproc.COLOR_BGR2HSV);                        // HSV色空間に変換
        Core.inRange(hsv, new Scalar(100, 10, 0), new Scalar(140, 255, 255), mask);    // 緑色領域のマスク作成
        im.copyTo(im2, mask);                                                                    // マスクを 用いて入力画像から緑色領域を抽出
        Imgcodecs.imwrite("tanaka.jpg", im2);                                                // 画像の出力

        Core.bitwise_not(mask,mask);
        Mat im3 = new Mat();
        im.copyTo(im3, mask);                                                                    // マスクを 用いて入力画像から緑色領域を抽出
        Imgcodecs.imwrite("tanaka2.jpg", im3);                                                // 画像の出力
    }
}
