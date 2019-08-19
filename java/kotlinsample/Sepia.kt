package kotlinsample

import java.io.IOException

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs
import org.scijava.nativelib.NativeLoader

class Sepia {

    @Throws(IOException::class)
    fun run(input: String, output: String) {
        // sepia kernel
        val kernel = Mat(3, 3, CvType.CV_32F)
        kernel.put(0, 0, 0.272, 0.534, 0.131, 0.349, 0.686, 0.168, 0.393, 0.769, 0.189)

        val destination = Mat()
        Core.transform(Imgcodecs.imread(input), destination, kernel)
        Imgcodecs.imwrite(output, destination)
    }

    companion object {

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME)
            Sepia().run("data/marcel.jpg", "target/marcel_sepia.jpg")
            println("A new sepia cat is in target/marcel_sepia.jpg")
        }
    }
}
