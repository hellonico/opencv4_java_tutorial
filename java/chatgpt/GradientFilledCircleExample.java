package chatgpt;

import org.opencv.core.*;
import origami.Origami;

public class GradientFilledCircleExample {
    public static void main(String[] args) {
        // Load the OpenCV native library
        Origami.init();

        // Create a new image matrix
        Mat image = new Mat(new Size(400, 400), CvType.CV_8UC3);

        // Define the center and radius of the circle
        Point center = new Point(100, 200);
        int radius = 150;

        // Define the gradient colors
        Scalar startColor = new Scalar(255, 150, 0); // Red
        Scalar endColor = new Scalar(100, 50, 0); // Blue

        // Create a gradient-filled circle
        drawGradientCircle(image, center, radius, startColor, endColor);

        // Display the image
        imshow("Gradient Filled Circle", image);
    }

    public static void drawGradientCircle(Mat image, Point center, int radius, Scalar startColor, Scalar endColor) {
        // Calculate the distance from the center for each pixel
        double maxDistance = Math.sqrt(image.rows() * image.rows() + image.cols() * image.cols());

        // Iterate over each pixel in the image
        for (int y = 0; y < image.rows(); y++) {
            for (int x = 0; x < image.cols(); x++) {
                // Calculate the distance from the center
                double distance = Math.sqrt(Math.pow(x - center.x, 2) + Math.pow(y - center.y, 2));

                // Interpolate the color based on the distance
                double t = distance / maxDistance;
                double r = startColor.val[0] + (endColor.val[0] - startColor.val[0]) * t;
                double g = startColor.val[1] + (endColor.val[1] - startColor.val[1]) * t;
                double b = startColor.val[2] + (endColor.val[2] - startColor.val[2]) * t;

                // Set the color of the pixel
                image.put(y, x, r, g, b);
            }
        }
    }

    public static void imshow(String windowName, Mat image) {
        // Create an OpenCV window
        org.opencv.highgui.HighGui.namedWindow(windowName, org.opencv.highgui.HighGui.WINDOW_NORMAL);

        // Display the image in the window
        org.opencv.highgui.HighGui.imshow(windowName, image);

        // Wait for a key press and close the window
        org.opencv.highgui.HighGui.waitKey(0);
        org.opencv.highgui.HighGui.destroyAllWindows();
    }
}
