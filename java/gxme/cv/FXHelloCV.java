package gxme.cv;

import org.opencv.core.Core;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import origami.Origami;

import java.io.IOException;
import java.net.URL;

/**
 * @author <a href="mailto:luigi.derussis@polito.it">Luigi De Russis</a>
 *
 */
public class FXHelloCV extends Application
{
	
	@Override
	public void start(Stage primaryStage) throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fx/FXHelloCV.fxml"));

			BorderPane rootElement = (BorderPane) loader.load();
			Scene scene = new Scene(rootElement, 800, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("JavaFX meets OpenCV");
			primaryStage.setScene(scene);
			primaryStage.show();
			
			FXHelloCVController controller = loader.getController();
			primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we)
				{
					controller.setClosed();
				}
			}));
	}

	public static void main(String[] args)
	{
		Origami.init();
		launch(args);
	}
}
