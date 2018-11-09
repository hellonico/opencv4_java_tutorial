package objectdetection.templatematching;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

import java.io.IOException;

public class TemplateMatching {

	public static void main(String[] args) throws IOException {

		NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String filePath="data/detectionimages/";
        Mat source = Imgcodecs.imread(filePath+"kapadokya.jpg");
        Mat template=Imgcodecs.imread(filePath+"balon.jpg");
	
		Mat outputImage=new Mat();
        Imgproc.matchTemplate(source, template, outputImage, Imgproc.TM_CCOEFF);

        MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc=mmr.maxLoc;

        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(255, 255, 255));

        Imgcodecs.imwrite("sonuc.jpg", source);

	}

}
