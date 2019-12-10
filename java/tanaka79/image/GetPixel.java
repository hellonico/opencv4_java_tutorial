package tanaka79.image;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

public class GetPixel {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");                    // 入力画像の取得

        // RGBカラー画像の画素値を取得
        double[] data = new double[3];
        data = im.get(100, 200);
        System.out.println("Blue：" + data[0]);
        System.out.println("Green：" + data[1]);
        System.out.println("Red：" + data[2]);

        // グレースケール画像の画素値を取得
        Mat gray = new Mat();
        Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY); // 画像のグレースケール変換
        double[] data2 = new double[1];
        data2 = gray.get(100, 200);
        System.out.println("Gray：" + data2[0]);
    }
}
