package kotlinsample

import java.io.File
import org.opencv.core.Core.*
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs.*
import org.scijava.nativelib.NativeLoader
import org.scijava.nativelib.NativeLoader.*

object Sepia {

    @JvmStatic
    fun main(args: Array<String>) {
        loadLibrary(NATIVE_LIBRARY_NAME)
        val filename = args.elementAtOrElse(0, { _ -> "marcel.jpg" });
        val out = args.elementAtOrElse(1, {_-> "target"})

        val source = imread(filename)
        val kernel = Mat(3, 3, CvType.CV_32F)
        kernel.put(0, 0,
                // green
                0.272, 0.534, 0.131,
                // blue
                0.349, 0.686, 0.168,
                // red
                0.393, 0.769, 0.189)
        val destination = Mat()
        transform(source, destination, kernel)

        imwrite("$out/sepia_" + File(filename).name, destination)
    }
}