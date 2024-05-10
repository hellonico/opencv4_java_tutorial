package chatgpt;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import origami.Origami;

import java.util.ArrayList;
import java.util.List;

public class GradientFilledRectangleExample {
    public static void main(String[] args) {
        // Load the OpenCV native library
        Origami.init();

        // Create a new image matrix
        Mat image = new Mat(new Size(400, 400), CvType.CV_8UC3);

        // Define the center and radius of the circle
        Point center = new Point(200, 200);
        int radius = 150;

        // Define the gradient colors
        Scalar startColor = new Scalar(255, 0, 0); // Red
        Scalar endColor = new Scalar(0, 0, 255); // Blue

        // Create a gradient-filled circle
        drawGradientCircle(image, center, radius, startColor, endColor);

        // Display the image
        imshow("Gradient Filled Circle", image);
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

    public static void drawGradientCircle(Mat image, Point center, int radius, Scalar startColor, Scalar endColor) {
        // Convert the circle into a polygon approximation
        List<Point> points = new ArrayList<>();
        for (double angle = 0; angle < 360; angle += 1) {
            double x = center.x + radius * Math.cos(Math.toRadians(angle));
            double y = center.y + radius * Math.sin(Math.toRadians(angle));
            points.add(new Point(x, y));
        }

        // Create a list of MatOfPoint objects
        List<MatOfPoint> contourList = new ArrayList<>();
        contourList.add(new MatOfPoint(points.toArray(new Point[0])));

        // Calculate the color increment per pixel
        double[] colorIncrement = new double[3];
        for (int i = 0; i < 3; i++) {
            colorIncrement[i] = (endColor.val[i] - startColor.val[i]) / radius;
        }

        // Draw the gradient-filled circle
        for (int r = 0; r < radius; r++) {
            Scalar currentColor = new Scalar(startColor.val[0] + colorIncrement[0] * r,
                    startColor.val[1] + colorIncrement[1] * r,
                    startColor.val[2] + colorIncrement[2] * r);
            Imgproc.fillPoly(image, contourList, currentColor);
        }
    }

}

