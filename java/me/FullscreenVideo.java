package me;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.Callable;

import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.scijava.nativelib.NativeLoader;

import origami.Origami;
import picocli.CommandLine;

@CommandLine.Command(name = "fullscreenvideo", version="1.0.0", mixinStandardHelpOptions = true, description = "Play A video fullscreen")
public class FullscreenVideo extends JFrame implements Callable<Integer> {


    private static final long serialVersionUID = -5957072069813017161L;
    BufferedImage image;

    @CommandLine.Option(names = {"-i", "--input"}, description = "Input Image", required = true, defaultValue="data/lexus.mpg")
    File videoFile;

    @CommandLine.Option(names = {"-f", "--fullscreen"}, description = "Fullscreen", required = false, defaultValue = "false")
    Boolean fullscreen;

    public static void main(String args[]) throws Exception {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        new CommandLine(new FullscreenVideo()).execute(args);
    }

    public Integer call() {
        VideoCapture camera = new VideoCapture(videoFile.getAbsolutePath());
        Mat frame = new Mat();
        Dimension di = getDimensions(camera, this, fullscreen);
        if (!camera.isOpened()) {
            throw new RuntimeException("Cannot read file");
        } else {

//            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
//            this.setUndecorated(true);
            this.pack();
            this.setVisible(true);

            while (camera.grab()) {
                camera.read(frame);
                if(frame.empty()) {
                    break;
                }
                Mat dst = new Mat();
                Imgproc.resize(frame, dst, new Size((int) di.getWidth(), 50 + (int) di.getHeight()));
                this.image = Origami.matToBufferedImage(dst);
                this.repaint();
            }
        }
        camera.release();
        return 0;
    }

    private Dimension getDimensions(VideoCapture camera, FullscreenVideo t, boolean fullscreen) {
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

    public FullscreenVideo() {
        super();
    }

}