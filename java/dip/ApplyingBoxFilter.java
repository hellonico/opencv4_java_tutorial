package dip;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.io.IOException;

public class ApplyingBoxFilter {

    static Mat createKernelOfSize(int kernelSize) {
        Mat kernel = Mat.ones(kernelSize, kernelSize, CvType.CV_32F);

        for (int i = 0; i < kernel.rows(); i++) {
            for (int j = 0; j < kernel.cols(); j++) {

                double[] m = kernel.get(i, j);

                for (int k = 0; k < m.length; k++) {
                    m[k] = m[k] / (kernelSize * kernelSize);
                }
                kernel.put(i, j, m);
            }
        }
        return kernel;
    }

    public static void main(String[] args) throws IOException {
        Origami.init();
        Mat source = Imgcodecs.imread("data/dip/grayscale.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat destination = new Mat(source.rows(), source.cols(), source.type());

        Mat kernel5 = createKernelOfSize(5);
        Imgproc.filter2D(source, destination, -1, kernel5);
        Imgcodecs.imwrite("boxfilterKernel5.jpg", destination);

        Mat kernel9 = createKernelOfSize(9);
        Imgproc.filter2D(source, destination, -1, kernel9);
        Imgcodecs.imwrite("boxfilterKernel9.jpg", destination);
    }
}
