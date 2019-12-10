package tanaka79;

import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import origami.Origami;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.rectangle;

public class AnimeFaceDetect {
    public static void main(String[] args) {

        Origami.init();
        // 入力画像の取得
        Mat im = imread("data/lupin3.jpeg");
        // カスケード分類器でアニメ顔探索
        CascadeClassifier faceDetector = new CascadeClassifier("data/nagadomi/lbpcascade_animeface.xml");
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(im, faceDetections);
        // 見つかったアニメ顔を矩形で囲む
        for (Rect rect : faceDetections.toArray()) {
            rectangle(im, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 5);
        }
        // 結果を保存
        imwrite("anime.png", im);
    }
}
