package sample

import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.scijava.nativelib.NativeLoader
import origami.Origami

object HelloCv {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        Origami.init()
        val hello = Mat.eye(3, 3, CvType.CV_8UC1)
        println(hello.dump())
    }
}
