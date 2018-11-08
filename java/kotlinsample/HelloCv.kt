package kotlinsample

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.scijava.nativelib.NativeLoader

object HelloCv {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME)
        val hello = Mat.eye(3, 3, CvType.CV_8UC1)
        println(hello.dump())
    }
}
