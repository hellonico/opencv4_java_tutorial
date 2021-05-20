package sample


import org.opencv.core.Core.*
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs.*
import org.scijava.nativelib.NativeLoader.*
import origami.Origami

import java.io.IOException

object AddingBorder {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        Origami.init()

        val source = imread("data/dip/digital_image_processing.jpg")
        val destination = Mat(source.rows(), source.cols(), source.type())

        val top = source.rows() / 20
        val bottom = source.rows() / 20
        val left = source.cols() / 20
        val right = source.cols() / 20

        copyMakeBorder(source, destination, top, bottom, left, right, BORDER_WRAP)
        imwrite("borderWrap.jpg", destination)

        copyMakeBorder(source, destination, top, bottom, left, right, BORDER_REFLECT)
        imwrite("borderReflect.jpg", destination)

        copyMakeBorder(source, destination, top, bottom, left, right, BORDER_REPLICATE)
        imwrite("borderReplicate.jpg", destination)

    }
}
