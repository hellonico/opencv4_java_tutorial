package tobybreckon;
// ********************************************************

// Example : displaying an image from file

// Author : Toby Breckon, toby.breckon@durham.ac.uk

// Copyright (c) 2015 Durham University
// License : LGPL - http://www.gnu.org/licenses/lgpl.html

// ********************************************************

// import required OpenCV components

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.scijava.nativelib.NativeLoader;

// ********************************************************

public class SmoothImage {
    static {
        try {
            // Load the native OpenCV library
            NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {

        // load the Core OpenCV library by name

        // create a display window using an Imshow object

        Imshow window1 = new Imshow("My Image");

        // load an image from file (read and decode JPEG file)

        Mat inputImg = Imgcodecs.imread("data/lena.jpg");

        // create an output object

        Mat outputImg = new Mat();

        // smooth the image

        Size filter = new Size(5, 5);
        Imgproc.GaussianBlur(inputImg, outputImg, filter, 0, 0, Core.BORDER_DEFAULT);

        // display image

        window1.showImage(outputImg);

    }
}

// ********************************************************
