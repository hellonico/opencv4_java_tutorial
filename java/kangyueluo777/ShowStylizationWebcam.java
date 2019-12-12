package kangyueluo777;

import org.opencv.core.Mat;
import org.opencv.photo.Photo;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

import javax.swing.*;


public class ShowStylizationWebcam {


    static {
        Origami.init();
    }

    public static void main(String arg[]) {
        VideoCapture capture = new VideoCapture();
        capture.open(0);
        JFrame frame1 = new JFrame("show image");
        frame1.setTitle("Webcam");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(640, 480);
        frame1.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());
        JLabel label = new JLabel();
        frame1.setContentPane(label);
        frame1.setVisible(true);

        JFrame frame2 = new JFrame("show image");
        frame2.setTitle("從webcam讀取影像至Java Swing視窗2");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(640, 480);
        frame2.setBounds(160, 0, frame1.getWidth(), frame1.getHeight());
//        Panel panel2 = new Panel();
        JLabel label2 = new JLabel();
        frame2.setContentPane(label2);
        frame2.setVisible(true);

        JFrame frame3 = new JFrame("show image");
        frame3.setTitle("從webcam讀取影像至Java Swing視窗2");
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setSize(640, 480);
        frame3.setBounds(280, 0, frame1.getWidth(), frame1.getHeight());
        JLabel label3 = new JLabel();
        frame3.setContentPane(label3);
        frame3.setVisible(true);
        if (!capture.isOpened()) {
            System.out.println("Error");
        } else {
            Mat webcam_image = new Mat();


            capture.read(webcam_image);
            frame1.setSize(webcam_image.width() + 40, webcam_image.height() + 60);
            frame2.setSize(webcam_image.width() + 50, webcam_image.height() + 70);
            frame3.setSize(webcam_image.width() + 60, webcam_image.height() + 80);
            while (true) {
                capture.read(webcam_image);
                //System.out.println("從webcam讀取影像至Java Swing視窗");

                Mat stylizationResult1 = new Mat();
                Mat stylizationResult2 = new Mat();

                Photo.stylization(webcam_image, stylizationResult1, 50.0f, 0.1f);
                Photo.stylization(webcam_image, stylizationResult2, 200.0f, 0.7f);

//                panel1.setimagewithMat(webcam_image);
//                panel2.setimagewithMat(stylizationResult1);
//                panel3.setimagewithMat(stylizationResult2);
                label.setIcon(new ImageIcon(Origami.matToBufferedImage(webcam_image)));
                label2.setIcon(new ImageIcon(Origami.matToBufferedImage(stylizationResult1)));
                label3.setIcon(new ImageIcon(Origami.matToBufferedImage(stylizationResult2)));
                frame1.repaint();
                frame2.repaint();
                frame3.repaint();
            }
        }
    }
}