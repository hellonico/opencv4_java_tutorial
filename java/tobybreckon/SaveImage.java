package tobybreckon;
// ********************************************************

// Example : saving an image from file

// Author : Toby Breckon, toby.breckon@durham.ac.uk

// Copyright (c) 2015 Durham University
// License : LGPL - http://www.gnu.org/licenses/lgpl.html

// ********************************************************

// import required OpenCV components

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.imgcodecs.Imgcodecs;

// ********************************************************

public class SaveImage {

    public static void main(String[] args) {

        // load the Core OpenCV library by name

        // load an image from file (read and decode JPEG file)

        Mat inputImg = Imgcodecs.imread("data/lena.jpg");

        // create an output object

        Mat outputImg = new Mat();

        // invert the image

        Core.bitwise_not(inputImg, outputImg);

        // write image to file (first setting up the compression quality to use)

        MatOfInt params = new MatOfInt();
        params.fromArray(new int[] { Imgcodecs.CV_IMWRITE_JPEG_QUALITY, 75, 0 });

        System.out.println("Saving image to file ....");

        Imgcodecs.imwrite("lena_inverted.jpg", outputImg, params);

    }
}

// ********************************************************
