package gxme.cv;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.embed.swing.SwingFXUtils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import origami.Origami;

/**
 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
 * @author <a href="http://max-z.de">Maximilian Zuleger</a> (minor fixes)
 *
 */
public class FXHelloCVController
{

	@FXML
	private Button button;
	@FXML
	private ImageView currentFrame;
	
	private ScheduledExecutorService timer;
	private VideoCapture capture = new VideoCapture();
	private boolean cameraActive = false;
	private static int cameraId = 0;

	@FXML
	protected void startCamera(ActionEvent event)
	{
		if (!this.cameraActive)
		{

			this.capture.open(cameraId);
			if (this.capture.isOpened())
			{
				this.cameraActive = true;
				Runnable frameGrabber = () -> {
					Mat frame = grabFrame();
					Image imageToShow =
							SwingFXUtils.toFXImage(Origami.matToBufferedImage(frame), null);
					updateImageView(currentFrame, imageToShow);
				};
				
				this.timer = Executors.newSingleThreadScheduledExecutor();
				this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
				this.button.setText("Stop Camera");
			}
			else
			{
				System.err.println("Impossible to open the camera connection...");
			}
		}
		else
		{
			this.cameraActive = false;
			this.button.setText("Start Camera");
			this.stopAcquisition();
		}
	}

	private Mat grabFrame()
	{

		Mat frame = new Mat();
		if (this.capture.isOpened())
		{
			try
			{
				this.capture.read(frame);
				if (!frame.empty())
				{
					Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);
				}
				
			}
			catch (Exception e)
			{
				System.err.println("Exception during the image elaboration: " + e);
			}
		}
		
		return frame;
	}

	private void stopAcquisition()
	{
		if (this.timer!=null && !this.timer.isShutdown())
		{
			try
			{
				this.timer.shutdown();
				this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException e)
			{
				System.err.println("Exception in stopping the frame capture, trying to release the camera now... " + e);
			}
		}
		
		if (this.capture.isOpened())
		{
			this.capture.release();
		}
	}

	private void updateImageView(ImageView view, Image image)
	{
		onFXThread(view.imageProperty(), image);
	}

	public static <T> void onFXThread(final ObjectProperty<T> property, final T value)
	{
		Platform.runLater(() -> {
			property.set(value);
		});
	}

	protected void setClosed()
	{
		this.stopAcquisition();
	}
	
}
