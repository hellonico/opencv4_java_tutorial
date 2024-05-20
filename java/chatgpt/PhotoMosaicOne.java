package chatgpt;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import origami.Filter;

import java.util.ArrayList;
import java.util.List;

public class PhotoMosaicOne implements Filter {

    // Define the size of the mosaic grid
    int gridSize = 200; // Adjust this value based on desired grid size
    double alpha = 1.1;
    int beta = 50;

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public int getBeta() {
        return beta;
    }

    public void setBeta(int beta) {
        this.beta = beta;
    }
    //
//    public static void main(String[] args) {
//        // Load the image
//        Mat image = Imgcodecs.imread(args[0]);
//
//        Mat brightenedImage = increaseBrightness(image, 1.1, 50);
//
//        // Create the photomosaic
//        Mat photomosaic = createPhotoMosaic(brightenedImage, gridSize);
//
//        // Save the resulting photomosaic
//        Imgcodecs.imwrite("photomosaicone.jpg", photomosaic);
//    }


    private static Mat createPhotoMosaic(Mat image, int gridSize) {
        // Resize the image once to improve efficiency
        Mat resizedImage = new Mat();
        Imgproc.resize(image, resizedImage, new Size((double) image.width() / gridSize, (double) image.height() / gridSize));

        int rows = image.rows();
        int cols = image.cols();
        int cellWidth = cols / gridSize;
        int cellHeight = rows / gridSize;

        Mat result = new Mat(rows, cols, image.type());

//        int totalCells = gridSize * gridSize;
//        int completedCells = 0;

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                // Extract the grid cell
                Rect rect = new Rect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                Mat cell = new Mat(image, rect);

                // Clone the resized image for HSV adjustment
                Mat adjustedImage = resizedImage.clone();

                double averageHue = calculateAverageHue(cell);
//                System.out.println(averageHue);
                adjustedImage = adjustHue(adjustedImage, averageHue);

//                Scalar averageHSV = calculateAverageHSV(cell);
//                System.out.println(averageHSV);
//                adjustedImage = adjustHSV(adjustedImage, averageHSV);

                // Place the adjusted image in the result
                Mat submat = result.submat(rect);
                adjustedImage.copyTo(submat);


                // Release Mats to free memory
                submat.release();
//                cell.release();
                adjustedImage.release();
//                resizedImage.release();

                // Increment completed cell count and print progress
//                completedCells++;
//                double progressPercentage = (double) completedCells / totalCells * 100;
//                System.out.printf("Progress: [%d] %.2f%%\n", completedCells, progressPercentage);
            }
        }

        return result;
    }

    private static Scalar calculateAverageHSV(Mat cell) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(cell, hsv, Imgproc.COLOR_BGR2HSV);

        List<Mat> channels = new ArrayList<>();
        Core.split(hsv, channels);

        Scalar meanHue = Core.mean(channels.get(0));
        Scalar meanSaturation = Core.mean(channels.get(1));
        Scalar meanValue = Core.mean(channels.get(2));

        // Release Mats to free memory
        hsv.release();

        return new Scalar(meanHue.val[0], meanSaturation.val[0], meanValue.val[0]);
    }


    private static double calculateAverageHue(Mat cell) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(cell, hsv, Imgproc.COLOR_BGR2HSV);

        List<Mat> channels = new ArrayList<>();
        Core.split(hsv, channels);
        Mat hue = channels.get(0);

        Scalar meanScalar = Core.mean(hue);
        hue.release();
        hsv.release();

        return meanScalar.val[0];
    }

    private static Mat adjustHue(Mat image, double targetHue) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

        List<Mat> channels = new ArrayList<>();
        Core.split(hsv, channels);
        Mat hue = channels.get(0);

        hue.setTo(new Scalar(targetHue));

        Core.merge(channels, hsv);
        Mat result = new Mat();
        Imgproc.cvtColor(hsv, result, Imgproc.COLOR_HSV2BGR);

        hue.release();
        hsv.release();

        return result;
    }

    private static Mat adjustHSV(Mat image, Scalar targetHSV) {
        Mat hsv = new Mat();
        Imgproc.cvtColor(image, hsv, Imgproc.COLOR_BGR2HSV);

        List<Mat> channels = new ArrayList<>();
        Core.split(hsv, channels);

        // Adjust the HSV channels to match the target HSV values
        channels.get(0).setTo(new Scalar(targetHSV.val[0]));
        channels.get(1).setTo(new Scalar(targetHSV.val[1]));
        channels.get(2).setTo(new Scalar(targetHSV.val[2]));

        Core.merge(channels, hsv);

        Mat result = new Mat();
        Imgproc.cvtColor(hsv, result, Imgproc.COLOR_HSV2BGR);

        // Release Mats to free memory
        hsv.release();

        return result;
    }

    private static Mat increaseBrightness(Mat image, double alpha, double beta) {
        Mat newImage = new Mat();
        image.convertTo(newImage, -1, alpha, beta);
        return newImage;
    }

    @Override
    public Mat apply(Mat image) {
        Mat brightenedImage = increaseBrightness(image, alpha, beta);
        Mat photomosaic = createPhotoMosaic(brightenedImage, gridSize);
        return photomosaic;
    }
}
