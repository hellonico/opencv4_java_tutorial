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
import org.opencv.imgcodecs.Imgcodecs;
import org.scijava.nativelib.NativeLoader;

// ********************************************************

public class DisplayImage {
    static {
        try {
            // Load the native OpenCV library
            NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {

        // load the Core OpenCV library by name

        // load an image from file (read and decode JPEG file)

        Mat inputImage = Imgcodecs.imread("data/lena.jpg");

        // create a display window using an Imshow object

        Imshow ims1 = new Imshow("My Image");

        // display image

        ims1.showImage(inputImage);

    }
}

// ********************************************************
