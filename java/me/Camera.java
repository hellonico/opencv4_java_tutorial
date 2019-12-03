package me;

import me.filters.Cartoon;
import me.filters.Filter;
import me.filters.LUTCartoon;
import me.filters.Vintage;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import origami.Origami;

import java.util.Arrays;
import java.util.List;

public class Camera {
    private static String myFilter= "me.filters.EnhanceImageSharpness";

    static void cameraSettings(VideoCapture cap) {
        String settings = String.format(
                "Name:\t\t%s\nWidth:\t\t%s\nHeight:\t\t%s\nFPS:\t\t%s\nAperture:\t%s\nAutofocus:\t%s\nGain:\t\t%s\nGamma:\t\t%s\nBrightness:\t%s\nBackend:\t%s\nBacklight:\t%s\nContrast:\t%s\nSaturation:\t%s\nSharpness:\t%s\nZoom:\t\t%s\nBuffersize:\t%s\n",
                "", cap.get(Videoio.CAP_PROP_FRAME_WIDTH), cap.get(Videoio.CAP_PROP_FRAME_HEIGHT),
                cap.get(Videoio.CAP_PROP_FPS), cap.get(Videoio.CAP_PROP_APERTURE), cap.get(Videoio.CAP_PROP_AUTOFOCUS),
                cap.get(Videoio.CAP_PROP_GAIN), cap.get(Videoio.CAP_PROP_GAMMA), cap.get(Videoio.CAP_PROP_BRIGHTNESS),
                cap.get(Videoio.CAP_PROP_BACKEND), cap.get(Videoio.CAP_PROP_BACKLIGHT),
                cap.get(Videoio.CAP_PROP_CONTRAST), cap.get(Videoio.CAP_PROP_SATURATION),
                cap.get(Videoio.CAP_PROP_SHARPNESS), cap.get(Videoio.CAP_PROP_ZOOM),
                cap.get(Videoio.CAP_PROP_BUFFERSIZE));
        System.out.println(settings);

    }

    public static void main(String[] args) throws Exception {
        Origami.init();
        Core.setNumThreads(4);

        VideoCapture cap = new VideoCapture(0);

        if (!cap.isOpened())
            throw new RuntimeException("error cannot any open camera");

        cap.set(Videoio.CAP_PROP_FRAME_WIDTH, 424.0);
        cap.set(Videoio.CAP_PROP_FRAME_HEIGHT, 240.0);
        cameraSettings(cap);
        Mat matFrame = new Mat();
        ImShow ims = new ImShow("Camera", 800, 300);
        Filter cf = (Filter) Class.forName(myFilter).newInstance();

        while (cap.grab()) {
            cap.retrieve(matFrame);
//            ims.showImage(threshing(matFrame));
//             ims.showImage(graying(matFrame));
            // ims.showImage(enhance(matFrame, 10, 0.01, 36, 10, 0.01));
//            ims.showImage(cf.apply(matFrame));
            List<Mat> mats = Arrays.asList(matFrame, cf.apply(matFrame));
            Mat result = new Mat();
            Core.hconcat(mats,result);
            ims.showImage(result);

        }

        cap.release();
    }

}
