package objectdetection.faceandeye;

import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import origami.Origami;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DetectFace {
 
	static JFrame frame;
	static JLabel lbl;
	static ImageIcon icon;
 
	public static void main(String[] args) throws IOException {
		Origami.init();

		CascadeClassifier cascadeFaceClassifier = new CascadeClassifier(
				"data/haarcascades/haarcascade_frontalface_default.xml");
		CascadeClassifier cascadeEyeClassifier = new CascadeClassifier(
				"data/haarcascades/haarcascade_eye.xml");
		
		CascadeClassifier cascadeNoseClassifier = new CascadeClassifier(
				"data/haarcascades/haarcascade_mcs_nose.xml");

		VideoCapture videoDevice = new VideoCapture();
		videoDevice.open(0);
		if (videoDevice.isOpened()) {

			while (true) {		
				Mat frameCapture = new Mat();
				videoDevice.read(frameCapture);
				

				MatOfRect faces = new MatOfRect();
				cascadeFaceClassifier.detectMultiScale(frameCapture, faces);								
				for (Rect rect : faces.toArray()) {

					Imgproc.putText(frameCapture, "Face", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));								
					Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(0, 100, 0),3);
				}

				MatOfRect eyes = new MatOfRect();
				cascadeEyeClassifier.detectMultiScale(frameCapture, eyes);
				for (Rect rect : eyes.toArray()) {
					Imgproc.putText(frameCapture, "Eye", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));
					Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(200, 200, 100),2);
				}

				MatOfRect nose = new MatOfRect();
				cascadeNoseClassifier.detectMultiScale(frameCapture, nose);
				for (Rect rect : nose.toArray()) {

					Imgproc.putText(frameCapture, "Nose", new Point(rect.x,rect.y-5), 1, 2, new Scalar(0,0,255));				
					Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
							new Scalar(50, 255, 50),2);
				}
				

				PushImage(ConvertMat2Image(frameCapture));
				System.out.println(String.format("%s FACES %s EYE %s NOSE detected.", faces.toArray().length,eyes.toArray().length,nose.toArray().length));
			}
		} else {
			System.out.println("Video aygytyna ba?lanylamady.");
			return;
		}
	}

	private static BufferedImage ConvertMat2Image(Mat kameraVerisi) {
	
		
		MatOfByte byteMatVerisi = new MatOfByte();
		Imgcodecs.imencode(".jpg", kameraVerisi, byteMatVerisi);
		byte[] byteArray = byteMatVerisi.toArray();
		BufferedImage goruntu = null;
		try {
			InputStream in = new ByteArrayInputStream(byteArray);
			goruntu = ImageIO.read(in);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return goruntu;
	}
  	

	public static void PencereHazirla() {
		frame = new JFrame();
		frame.setLayout(new FlowLayout());
		frame.setSize(700, 600);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void PushImage(Image img2) {
		if (frame == null)
			PencereHazirla();
		if (lbl != null)
			frame.remove(lbl);
		icon = new ImageIcon(img2);
		lbl = new JLabel();
		lbl.setIcon(icon);
		frame.add(lbl);
		frame.revalidate();
	}
}