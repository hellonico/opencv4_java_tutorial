package tanaka79;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import origami.Origami;

public class Level {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");    // 入力画像の取得
        int n = 100;                                        // 大きいほど階調数が減少
        // 減色処理
        Size sz = im.size();
        for (int i = 0; i < sz.height; i++) {
            for (int j = 0; j < sz.width; j++) {
                double[] pixcel = im.get(i, j);
                pixcel[0] = ((int) pixcel[0] / n) * n + n / 2;
                pixcel[1] = ((int) pixcel[1] / n) * n + n / 2;
                pixcel[2] = ((int) pixcel[2] / n) * n + n / 2;
                im.put(i, j, pixcel);
            }
        }
        Imgcodecs.imwrite("tanaka.jpg", im);            // 画像データをJPG形式で保存
    }
}
