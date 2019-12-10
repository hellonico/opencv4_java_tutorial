package tanaka79;

import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import origami.Origami;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.rectangle;

public class FaceDetect {
    public static void main(String[] args) {

        Origami.init();
        // 入力画像の取得
        Mat im = imread("data/marcel2019.jpg");
        // カスケード分類器で顔探索
        CascadeClassifier faceDetector = new CascadeClassifier("data/haarcascades/haarcascade_frontalface_alt.xml");
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(im, faceDetections);
        // 見つかった顔を矩形で囲む
        for (Rect rect : faceDetections.toArray()) {
            rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 5);
        }
        // 結果を保存
        imwrite("tanaka.jpg", im);
    }
}
