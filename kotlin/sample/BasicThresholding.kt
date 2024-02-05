package sample

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs.*
import org.opencv.imgproc.Imgproc.*
import org.scijava.nativelib.NativeLoader.*
import origami.Origami
import java.io.IOException

object BasicThresholding {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        Origami.init()

        val source = imread("data/dip/digital_image_processing.jpg")
        val destination = Mat(source.rows(), source.cols(), source.type())

        threshold(source, destination, 127.0, 255.0, THRESH_TOZERO)
        imwrite("ThreshZero.jpg", destination)

        threshold(source, destination, 127.0, 255.0, THRESH_TOZERO_INV)
        imwrite("ThreshZeroInv.jpg", destination)

        threshold(source, destination, 127.0, 255.0, THRESH_BINARY)
        imwrite("ThreshBinary.jpg", destination)

        threshold(source, destination, 127.0, 255.0, THRESH_BINARY_INV)
        imwrite("ThreshBinaryInv.jpg", destination)


    }
}
