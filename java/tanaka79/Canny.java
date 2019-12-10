package tanaka79;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class Canny {
    public static void main(String[] args) {
        Origami.init();
        Mat gray = Imgcodecs.imread("data/lupin3.jpeg", 0);    // 入力画像の取得
        Imgproc.Canny(gray, gray, 100, 200, 3, true);
        Imgcodecs.imwrite("tanaka.jpg", gray);            // 画像データをJPG形式で保存
    }
}
