/**
 * https://github.com/spedepekka/java-opencv3-swing/
 * 
 *  */

package dnn.rt;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.scijava.nativelib.NativeLoader;

class Mat2Image {
    static {
        try {
            // Load the native OpenCV library
            NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (Exception e) {

        }
    }
    Mat mat;
    BufferedImage img;

    public Mat2Image() {
    }

    public Mat2Image(Mat mat) {
        getSpace(mat);
    }

    public void getSpace(Mat mat) {
        int type = 0;
        if (mat.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (mat.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        this.mat = mat;
        int w = mat.cols();
        int h = mat.rows();
        if (img == null || img.getWidth() != w || img.getHeight() != h || img.getType() != type)
            img = new BufferedImage(w, h, type);
    }

    BufferedImage getImage(Mat mat) {
        getSpace(mat);
        WritableRaster raster = img.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        mat.get(0, 0, data);
        return img;
    }
}

public class OpenCVVideoCapture extends JFrame {
    private JPanel contentPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    OpenCVVideoCapture frame = new OpenCVVideoCapture();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private OpenCVVideoCapture() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1280, 720);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        new MyThread().start();
    }

    private VideoCap videoCap = new VideoCap();

    public void paint(Graphics g) {
        g = contentPane.getGraphics();
        g.drawImage(videoCap.getOneFrame(), 0, 0, this);
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            for (;;) {
                repaint();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                }
            }
        }
    }
}

class VideoCap {

    VideoCapture cap;
    Mat2Image mat2Img = new Mat2Image();
    CaffeProcessor processor;

    VideoCap() throws IOException {
        cap = new VideoCapture();
        cap.open(0);
        processor = new CaffeProcessor();
    }

    private static String label(DnnObject obj) {
        return String.format("%s [%.0f%%] ", obj.objectName, obj.confidence * 100);
    }

    BufferedImage getOneFrame() {
        cap.read(mat2Img.mat);

        List<DnnObject> detectObject = processor.getObjectsInFrame(mat2Img.mat);
        for (DnnObject obj : detectObject) {
            Imgproc.rectangle(mat2Img.mat, obj.leftBottom, obj.rightTop, new Scalar(255, 0, 0), 1);
            Imgproc.putText(mat2Img.mat, label(obj), obj.leftBottom, Imgproc.FONT_HERSHEY_PLAIN, 1,
                    new Scalar(255, 0, 0));
        }
        return mat2Img.getImage(mat2Img.mat);
    }
}