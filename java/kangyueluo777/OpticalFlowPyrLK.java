package kangyueluo777;


import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

import javax.swing.*;
import java.util.List;


public class OpticalFlowPyrLK {

    static {
        Origami.init();
    }

    public static void main(String arg[]) throws Exception {

        JFrame frame2 = new JFrame("OpticalFlowPyrLK");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(640, 480);
        frame2.setBounds(300, 100, frame2.getWidth() + 50, 50 + frame2.getHeight());
//        Panel panel2 = new Panel();
        JLabel label = new JLabel();

        frame2.setContentPane(label);
        frame2.setVisible(true);
        //-- 2. Read the video stream

        VideoCapture capture = new VideoCapture();
        capture.open(0); //0表第1支CCD,1是第2支
        Mat webcam_image = new Mat();

        MatOfPoint2f mMOP2fptsThis = new MatOfPoint2f();
        MatOfPoint2f mMOP2fptsPrev = new MatOfPoint2f();
        MatOfPoint2f mMOP2fptsSafe = new MatOfPoint2f();
        MatOfFloat mMOFerr = new MatOfFloat();
        MatOfByte mMOBStatus = new MatOfByte();
        Mat matOpFlowThis = new Mat();
        Mat matOpFlowPrev = new Mat();
        List<Point> pts, corners, cornersThis, cornersPrev;
        List<Byte> byteStatus;
        int x, y, iLineThickness = 3;
        Point pt, pt1, pt2;
        Scalar colorOpticalFlow = new Scalar(0, 255, 0);

        Mat mRgba;


        capture.read(webcam_image);
        frame2.setSize(webcam_image.width() + 40, webcam_image.height() + 60);

        if (capture.isOpened()) {
            while (true) {
                Thread.sleep(200);


                capture.read(webcam_image);
                if (!webcam_image.empty()) {
                    mRgba = webcam_image.clone();

                    int qualityLevel = 40;
                    MatOfPoint MOPcorners = new MatOfPoint();

                    if (mMOP2fptsPrev.rows() == 0) {

                        // first time through the loop so we need prev and this mats
                        // plus prev points
                        // get this mat
                        Imgproc.cvtColor(mRgba, matOpFlowThis, Imgproc.COLOR_RGBA2GRAY);

                        // copy that to prev mat
                        matOpFlowThis.copyTo(matOpFlowPrev);

                        // get prev corners
                        Imgproc.goodFeaturesToTrack(matOpFlowPrev, MOPcorners, qualityLevel, 0.05, 20);
                        mMOP2fptsPrev.fromArray(MOPcorners.toArray());

                        // get safe copy of this corners
                        mMOP2fptsPrev.copyTo(mMOP2fptsSafe);
                    } else {
                        //Log.d("Baz", "Opflow");
                        // we've been through before so
                        // this mat is valid. Copy it to prev mat
                        matOpFlowThis.copyTo(matOpFlowPrev);

                        // get this mat
                        Imgproc.cvtColor(mRgba, matOpFlowThis, Imgproc.COLOR_RGBA2GRAY);

                        // get the corners for this mat
                        Imgproc.goodFeaturesToTrack(matOpFlowThis, MOPcorners, qualityLevel, 0.05, 20);
                        mMOP2fptsThis.fromArray(MOPcorners.toArray());

                        // retrieve the corners from the prev mat
                        // (saves calculating them again)
                        mMOP2fptsSafe.copyTo(mMOP2fptsPrev);

                        // and save this corners for next time through

                        mMOP2fptsThis.copyTo(mMOP2fptsSafe);
                    }


//                  Parameters:
//                      prevImg first 8-bit input image
//                      nextImg second input image
//                      prevPts vector of 2D points for which the flow needs to be found; point coordinates must be single-precision floating-point numbers.
//                      nextPts output vector of 2D points (with single-precision floating-point coordinates) containing the calculated new positions of input features in the second image; when OPTFLOW_USE_INITIAL_FLOW flag is passed, the vector must have the same size as in the input.
//                      status output status vector (of unsigned chars); each element of the vector is set to 1 if the flow for the corresponding features has been found, otherwise, it is set to 0.
//                      err output vector of errors; each element of the vector is set to an error for the corresponding feature, type of the error measure can be set in flags parameter; if the flow wasn't found then the error is not defined (use the status parameter to find such cases).

                    Video.calcOpticalFlowPyrLK(matOpFlowPrev, matOpFlowThis, mMOP2fptsPrev, mMOP2fptsThis, mMOBStatus, mMOFerr);

                    cornersPrev = mMOP2fptsPrev.toList();
                    cornersThis = mMOP2fptsThis.toList();
                    byteStatus = mMOBStatus.toList();

                    y = byteStatus.size() - 1;

                    for (x = 0; x < y; x++) {
                        if (byteStatus.get(x) == 1) {
                            pt = cornersThis.get(x);
                            pt2 = cornersPrev.get(x);

                            Imgproc.circle(mRgba, pt, 5, colorOpticalFlow, iLineThickness - 1);

                            Imgproc.line(mRgba, pt, pt2, colorOpticalFlow, iLineThickness);
                        }
                    }


                    label.setIcon(new ImageIcon(Origami.matToBufferedImage(mRgba)));
//                    panel2.setimagewithMat(mRgba);  //
                    frame2.repaint();
                } else {
                    System.out.println("無補抓任何畫面!");
                    break;
                }
            }
        }
        return;
    }

}