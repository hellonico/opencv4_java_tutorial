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
import origami.Camera;

public class OpenCVWebcam {

    public static void main(String args[]) throws InterruptedException {
        new Camera().fullscreen().run();
    }

}