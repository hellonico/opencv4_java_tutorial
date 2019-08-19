package webcam;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.scijava.nativelib.NativeLoader;

public class OpenCVWebcam extends JPanel {

    private static final long serialVersionUID = 2308883915053920237L;
    BufferedImage image;
    static {
        try {
            // Load the native OpenCV library
            NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (Exception e) {

        }
    }

    public static void main(String args[]) throws InterruptedException {

        OpenCVWebcam t = new OpenCVWebcam();
        VideoCapture camera = new VideoCapture(0);

        Mat frame = new Mat();
        camera.read(frame);

        if (!camera.isOpened()) {
            System.out.println("Error");
        } else {
            while (true) {

                if (camera.read(frame)) {

                    BufferedImage image = t.MatToBufferedImage(frame);

                    t.window(image, "Original Image", 0, 0);

                    t.window(t.grayscale(image), "Processed Image", 40, 60);

                    // t.window(t.loadImage("ImageName"), "Image loaded", 0, 0);

                    break;
                }
            }
        }
        camera.release();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public OpenCVWebcam() {
    }

    public OpenCVWebcam(BufferedImage img) {
        image = img;
    }

    // Show image on window
    public void window(BufferedImage img, String text, int x, int y) {
        JFrame frame0 = new JFrame();
        frame0.getContentPane().add(new OpenCVWebcam(img));
        frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame0.setTitle(text);
        frame0.setSize(img.getWidth(), img.getHeight() + 30);
        frame0.setLocation(x, y);
        frame0.setVisible(true);
    }

    // Load an image
    public BufferedImage loadImage(String file) {
        BufferedImage img;

        try {
            File input = new File(file);
            img = ImageIO.read(input);

            return img;
        } catch (Exception e) {
            System.out.println("erro");
        }

        return null;
    }

    // Save an image
    public void saveImage(BufferedImage img) {
        try {
            File outputfile = new File("Images/new.png");
            ImageIO.write(img, "png", outputfile);
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    // Grayscale filter
    public BufferedImage grayscale(BufferedImage img) {
        for (int i = 0; i < img.getHeight(); i++) {
            for (int j = 0; j < img.getWidth(); j++) {
                Color c = new Color(img.getRGB(j, i));

                int red = (int) (c.getRed() * 0.299);
                int green = (int) (c.getGreen() * 0.587);
                int blue = (int) (c.getBlue() * 0.114);

                Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);

                img.setRGB(j, i, newColor.getRGB());
            }
        }

        return img;
    }

    public BufferedImage MatToBufferedImage(Mat frame) {
        // Mat() to BufferedImage
        int type = 0;
        if (frame.channels() == 1) {
            type = BufferedImage.TYPE_BYTE_GRAY;
        } else if (frame.channels() == 3) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(frame.width(), frame.height(), type);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        frame.get(0, 0, data);

        return image;
    }

}