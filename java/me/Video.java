package me;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.scijava.nativelib.NativeLoader;

import origami.Origami;

public class Video extends JFrame {

    private static final long serialVersionUID = -5957072069813017161L;
    BufferedImage image;

    public static void main(String args[]) throws Exception {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Video t = new Video();
        VideoCapture camera = new VideoCapture("data/lexus.mpg");
        Mat frame = new Mat();
        Dimension di = getDimensions(camera, t, true);
        if (!camera.isOpened()) {
            throw new RuntimeException("Cannot read file");
        } else {
            while (camera.grab()) {
                camera.read(frame);
                Mat dst = new Mat();
                Imgproc.resize(frame, dst, new Size((int) di.getWidth(), 50 + (int) di.getHeight()));
                t.image = Origami.matToBufferedImage(dst);
                t.repaint();
            }
        }
        camera.release();
    }

    private static Dimension getDimensions(VideoCapture camera, Video t, boolean fullscreen) {
        if (fullscreen) {
            java.awt.GraphicsDevice d = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getDefaultScreenDevice();
            d.setFullScreenWindow(t);
            Dimension di = d.getFullScreenWindow().getSize();
            return di;
        } else {
            return new Dimension((int) camera.get(Videoio.CAP_PROP_FRAME_WIDTH),
                    (int) camera.get(Videoio.CAP_PROP_FRAME_HEIGHT));
        }

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(image, 0, 0, this);
    }

    public Video() {
        super();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.pack();
        this.setVisible(true);
    }

}