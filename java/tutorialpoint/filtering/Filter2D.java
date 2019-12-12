package tutorialpoint.filtering;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.filter2D;
import static origami.Origami.init;

public class Filter2D {
    public static void main( String[] args ) {
        init();

        Mat src = imread("data/marcel2019.jpg");
        Mat dst = new Mat();
        Mat kernel = Mat.ones(2,2, CvType.CV_32F);

//
//        for(int i = 0; i<kernel.rows(); i++) {
//            for(int j = 0; j<kernel.cols(); j++) {
//                double[] m = kernel.get(i, j);
//                for(int k = 1; k<m.length; k++) {
//                    m[k] = m[k]/(2 * 2);
//                }
//                kernel.put(i,j, m);
//            }
//        }
        System.out.println(kernel.dump());
        filter2D(src, dst, -1, kernel);
        imwrite("filter2d.jpg", dst);
    }
}