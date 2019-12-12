package kangyueluo777;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;

import static origami.Origami.matToBufferedImage;

public class EnhanceLocalContrast {
    static {
        Origami.init();
    }

    private JFrame frmjavaSwing;
    double alpha = 2;
    double beta = 50;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EnhanceLocalContrast window = new EnhanceLocalContrast();
                    window.frmjavaSwing.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public EnhanceLocalContrast() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        final Mat source = Imgcodecs.imread("data/marcel.jpg", Imgcodecs.IMREAD_GRAYSCALE);

        BufferedImage image = matToBufferedImage(source);

        frmjavaSwing = new JFrame();
        frmjavaSwing.setTitle("讀取影像至Java Swing視窗");
        frmjavaSwing.setBounds(100, 100, 520, 452);
        frmjavaSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmjavaSwing.getContentPane().setLayout(null);

        final JLabel showClipLimitValue = new JLabel("0.1");
        showClipLimitValue.setBounds(287, 10, 46, 15);
        frmjavaSwing.getContentPane().add(showClipLimitValue);

        final JLabel showTileGridSizeValue = new JLabel("1");
        showTileGridSizeValue.setBounds(297, 35, 46, 15);
        frmjavaSwing.getContentPane().add(showTileGridSizeValue);

        final JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(10, 68, 438, 340);
        lblNewLabel.setIcon(new ImageIcon(image));
        frmjavaSwing.getContentPane().add(lblNewLabel);

        final JSlider slider_beta = new JSlider();
        slider_beta.setValue(1);
        slider_beta.setMinimum(1);
        slider_beta.setBounds(85, 35, 200, 25);
        frmjavaSwing.getContentPane().add(slider_beta);

        final JSlider slider_alpha = new JSlider();
        slider_alpha.setMaximum(500);
        slider_alpha.setMinimum(1);
        slider_alpha.setValue(1);
        slider_alpha.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                //System.out.println(slider_alpha.getValue());
                showClipLimitValue.setText((float) slider_alpha.getValue() / 10 + "");
                BufferedImage newImage = matToBufferedImage(CLAHE((float) slider_alpha.getValue() / 10, slider_beta.getValue()));
                lblNewLabel.setIcon(new ImageIcon(newImage));
            }
        });
        slider_alpha.setBounds(85, 10, 200, 25);
        frmjavaSwing.getContentPane().add(slider_alpha);

        JLabel lblAlpha = new JLabel("clipLimit:");
        lblAlpha.setBounds(10, 20, 65, 15);
        frmjavaSwing.getContentPane().add(lblAlpha);

        JLabel lblBeta = new JLabel("tileGridSize:");
        lblBeta.setBounds(10, 45, 79, 15);
        frmjavaSwing.getContentPane().add(lblBeta);


        slider_beta.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                showTileGridSizeValue.setText(slider_beta.getValue() + "");
                BufferedImage newImage = matToBufferedImage(CLAHE((float) slider_alpha.getValue() / 10, slider_beta.getValue()));
                lblNewLabel.setIcon(new ImageIcon(newImage));
            }
        });


    }

    public Mat CLAHE(double clipLimit, double tileGridSize) {
        Mat source = Imgcodecs.imread("data/marcel.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        Mat dst = new Mat();
        CLAHE clahe = Imgproc.createCLAHE(clipLimit, new Size(tileGridSize, tileGridSize));
        clahe.apply(source, dst);
        return dst;

    }

}
