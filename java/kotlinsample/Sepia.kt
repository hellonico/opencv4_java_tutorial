package kotlinsample

import java.io.File
import org.opencv.core.Core.*
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.imgcodecs.Imgcodecs.*
import org.scijava.nativelib.NativeLoader

class Sepia {
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
                NativeLoader.loadLibrary(NATIVE_LIBRARY_NAME)
                val outputFolder = File("sepias")
                outputFolder.mkdirs()
                for (file in args) {
                    sepia(File(file), outputFolder)
                }
            }
        }
    }
}
