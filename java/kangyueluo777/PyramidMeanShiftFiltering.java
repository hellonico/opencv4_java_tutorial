package kangyueluo777;


import org.opencv.core.Mat;
import org.opencv.core.TermCriteria;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static origami.Origami.matToBufferedImage;


public class PyramidMeanShiftFiltering {

    static{
        Origami.init();
    }
    private JFrame frmjavaSwing;
    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    PyramidMeanShiftFiltering window = new PyramidMeanShiftFiltering();
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
    public PyramidMeanShiftFiltering() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        frmjavaSwing = new JFrame();
        frmjavaSwing.setTitle("PyramidMeanShiftFiltering");
        frmjavaSwing.setBounds(100, 100, 565, 550);
        frmjavaSwing.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmjavaSwing.getContentPane().setLayout(null);

        final JLabel showSpValue = new JLabel("17");
        showSpValue.setBounds(142, 20, 46, 15);
        frmjavaSwing.getContentPane().add(showSpValue);

        final JSlider slider_method = new JSlider();
        slider_method.setMinimum(1);
        slider_method.setMaximum(30);
        slider_method.setValue(17);

        slider_method.setBounds(26, 10, 106, 25);
        frmjavaSwing.getContentPane().add(slider_method);

        JLabel lblAlpha = new JLabel("SP");
        lblAlpha.setBounds(10, 20, 46, 15);
        frmjavaSwing.getContentPane().add(lblAlpha);

        final JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setBounds(10, 38, 517, 468);
        frmjavaSwing.getContentPane().add(lblNewLabel_2);

        JLabel lblSr = new JLabel("SR");
        lblSr.setBounds(176, 20, 46, 15);
        frmjavaSwing.getContentPane().add(lblSr);

        final JSlider slider = new JSlider();

        slider.setValue(30);
        slider.setMinimum(1);
        slider.setMaximum(50);
        slider.setBounds(199, 10, 112, 25);
        frmjavaSwing.getContentPane().add(slider);

        final JLabel showSrValue = new JLabel("30");
        showSrValue.setBounds(310, 20, 46, 15);
        frmjavaSwing.getContentPane().add(showSrValue);

        JLabel lblMaxlevel = new JLabel("maxLevel");
        lblMaxlevel.setBounds(342, 20, 65, 15);
        frmjavaSwing.getContentPane().add(lblMaxlevel);

        final JSlider slider_maxLevel = new JSlider();

        slider_maxLevel.setValue(1);
        slider_maxLevel.setMinimum(1);
        slider_maxLevel.setMaximum(10);
        slider_maxLevel.setBounds(404, 10, 106, 25);
        frmjavaSwing.getContentPane().add(slider_maxLevel);

        final JLabel labelMaxLevel = new JLabel("1");
        labelMaxLevel.setBounds(511, 20, 46, 15);
        frmjavaSwing.getContentPane().add(labelMaxLevel);

        slider_method.addChangeListener(arg0 -> {
            //System.out.println(slider_alpha.getValue());
            showSpValue.setText(slider_method.getValue()+"");

            BufferedImage newImage=matToBufferedImage(pyrMeanShiftFiltering(slider_method.getValue(),slider.getValue(),slider_maxLevel.getValue()));
            lblNewLabel_2.setIcon(new ImageIcon(newImage));
        });

        slider.addChangeListener(arg0 -> {
            showSrValue.setText(slider.getValue()+"");
            BufferedImage newImage=matToBufferedImage(pyrMeanShiftFiltering(slider_method.getValue(),slider.getValue(),slider_maxLevel.getValue()));
            lblNewLabel_2.setIcon(new ImageIcon(newImage));
        });
        slider_maxLevel.addChangeListener(e -> {
            labelMaxLevel.setText(slider_maxLevel.getValue()+"");
            BufferedImage newImage=matToBufferedImage(pyrMeanShiftFiltering(slider_method.getValue(),slider.getValue(),slider_maxLevel.getValue()));
            lblNewLabel_2.setIcon(new ImageIcon(newImage));
        });
    }
    public Mat pyrMeanShiftFiltering(int sp,int sr,int maxLevel){
        Mat source2 = Imgcodecs.imread("data/marcel.jpg");
        Mat output=new Mat();
        Imgproc.pyrMeanShiftFiltering(source2, output, sp, sr,maxLevel, new TermCriteria(TermCriteria.MAX_ITER| TermCriteria.EPS,50,0.001));
        return output;
    }

}

