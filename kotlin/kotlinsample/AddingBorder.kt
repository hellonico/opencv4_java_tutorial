package kotlinsample


import org.opencv.core.Core.*
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs.*
import org.scijava.nativelib.NativeLoader
import org.scijava.nativelib.NativeLoader.*

import java.io.IOException

object AddingBorder {
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        loadLibrary(NATIVE_LIBRARY_NAME)

        val source = imread("data/dip/digital_image_processing.jpg")
        val destination = Mat(source.rows(), source.cols(), source.type())

        val top: Int
        val bottom: Int
        val left: Int
        val right: Int
        top = (0.05 * source.rows()).toInt()
        bottom = (0.05 * source.rows()).toInt()
        left = (0.05 * source.cols()).toInt()
        right = (0.05 * source.cols()).toInt()

        copyMakeBorder(source, destination, top, bottom, left, right, BORDER_WRAP)
        imwrite("borderWrap.jpg", destination)

        copyMakeBorder(source, destination, top, bottom, left, right, BORDER_REFLECT)
        imwrite("borderReflect.jpg", destination)

        copyMakeBorder(source, destination, top, bottom, left, right, BORDER_REPLICATE)
        imwrite("borderReplicate.jpg", destination)

    }
}
