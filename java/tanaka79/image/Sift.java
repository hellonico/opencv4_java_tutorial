package tanaka79.image;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.Features2d;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.xfeatures2d.SIFT;
import origami.Origami;

public class Sift {
    public static void main(String[] args) {
        Origami.init();
        Mat im = Imgcodecs.imread("data/lupin3.jpeg");                    // 入力画像の取得
        Mat gray = new Mat();
        Imgproc.cvtColor(im, gray, Imgproc.COLOR_RGB2GRAY); // 画像のグレースケール変換

        // ------ SIFTの処理 ここから ------
//	    FeatureDetector siftDetector = FeatureDetector.create(FeatureDetector.SIFT);
//	    DescriptorExtractor siftExtractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);

        SIFT siftDetector = SIFT.create();

        MatOfKeyPoint kp = new MatOfKeyPoint();
        siftDetector.detect(gray, kp);

//        Mat des = new Mat(im.rows(), im.cols(), im.type());
//        siftExtractor.compute(gray, kp, des);

		// -- Draw keypoints
		Features2d.drawKeypoints(im, kp, im);

        Imgcodecs.imwrite("tanaka.jpg", im);                    // 画像の出力
    }
}
