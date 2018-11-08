package dip;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.scijava.nativelib.NativeLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class ImageShapeConversions {
    public static void main(String[] args) throws IOException {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        File input = new File("data/dip/digital_image_processing.jpg");
        BufferedImage image = ImageIO.read(input);

        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);

        Mat mat1 = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        Core.flip(mat, mat1, -1);

        byte[] data1 = new byte[mat1.rows() * mat1.cols() * (int) (mat1.elemSize())];
        mat1.get(0, 0, data1);
        BufferedImage image1 = new BufferedImage(mat1.cols(), mat1.rows(), 5);
        image1.getRaster().setDataElements(0, 0, mat1.cols(), mat1.rows(), data1);

        File outout = new File("hsv.jpg");
        ImageIO.write(image1, "jpg", outout);

    }
}
