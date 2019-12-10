package tanaka79;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import origami.Origami;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_SIMPLEX;
import static org.opencv.imgproc.Imgproc.putText;

public class DrawText {
    public static void main(String[] args) {

        Origami.init();
        // 入力画像の取得
        Mat im = imread("data/marcel2019.jpg");
        // 文字の描画
        putText(im, "Earth", new Point(60, 60), FONT_HERSHEY_SIMPLEX, 1.6f, new Scalar(20, 0, 200), 3);
        // 結果を保存
        imwrite("ouput.png", im);
    }
}
