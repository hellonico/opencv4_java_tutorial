package me.filters.android;

import origami.Filter;
import org.opencv.core.Mat;

/**
 * This is super slow
 */
public class Blending implements Filter {

    private Mat img2;
    double alpha = 40.0;

    public Blending(Mat img2, Mat mask) {

    }

    public Blending(Mat img2) {
        this.img2 = img2;
    }

    Mat regionBlending(Mat img, Mat img2, Mat mask) {
        Mat result = img2;

        for (int row = 0; row < img.rows(); row++) {
            for (int col = 0; col < img.cols(); col++) {
                double[] img1Pixel = img.get(row, col);
                double[] binaryPixel = mask.get(row, col);
                if (binaryPixel[0] == 255.0) {
                    result.put(row, col, img1Pixel);
                }
            }
        }
        return result;
    }

    Mat imageBlending(Mat img, Mat img2) {
        Mat result = img;

        for (int row = 0; row < img.rows(); row++) {
            for (int col = 0; col < img.cols(); col++) {
                double[] pixel1 = img.get(row, col);

                double[] pixel2 = img2.get(row, col);


                double fraction = alpha / 255.0;

                pixel1[0] = pixel1[0] * fraction + pixel2[0] * (1.0 - fraction);
                pixel1[1] = pixel1[1] * fraction + pixel2[1] * (1.0 - fraction);
                pixel1[2] = pixel1[2] * fraction + pixel2[2] * (1.0 - fraction);

                result.put(row, col, pixel1);
            }
        }
        return result;
    }
    @Override
    public Mat apply(Mat in) {
        return imageBlending(in,img2);
    }
}
