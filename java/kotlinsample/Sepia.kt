package kotlinsample

import java.io.File
import org.opencv.core.Core.*
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs.*
import org.scijava.nativelib.NativeLoader
import org.scijava.nativelib.NativeLoader.*

class MySepia {
    companion object {

        fun sepiaOne(file: File, outputFolder: File) {
            val source = imread(file.absolutePath)
            println("Input file of size ${source.width()}x${source.height()}")
            val destination = Mat()
            val kernel = Mat(3, 3, CvType.CV_32F)
                kernel.put(0, 0, 0.272, 0.534, 0.131, 0.349, 0.686, 0.168, 0.393, 0.769, 0.189)
            transform(source, destination, kernel)
            imwrite("${outputFolder.absolutePath}${File.separator}${file.name}", destination)
        }

        fun sepia(file: File, outputFolder: File) = if (file.isDirectory) {
            for (f in file.listFiles()) {
                try {
                    sepiaOne(f, outputFolder)
                } catch (e: Exception) {
                    println("Could not process ${f.absoluteFile}")
                }
            }
        } else {
            sepiaOne(file, outputFolder)
        }

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            if (args.size == 0)
                println("Usage: Sepia <file1> <file2> ...")
            else {
                loadLibrary(NATIVE_LIBRARY_NAME)
                val outputFolder = File("sepias")
                outputFolder.mkdirs()
                for (file in args) {
                    sepia(File(file), outputFolder)
                }
            }
        }
    }
}

/**
 * Using a kernel to get sepia picture
 */
object Sepia {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        loadLibrary(NATIVE_LIBRARY_NAME)
        val filename = "marcel.jpg"
        // String filename = args[0];
        val source = imread(filename)

        // mat is in BGR
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

        imwrite("target/sepia2_" + File(filename).name, destination)
    }
}