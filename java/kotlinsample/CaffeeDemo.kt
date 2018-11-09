package kotlinsample

import org.opencv.core.Core
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.dnn.Dnn
import org.opencv.imgcodecs.Imgcodecs
import org.scijava.nativelib.NativeLoader
import java.util.*

object CaffeeDemo {

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        genderDemo()

    }

    private fun genderDemo() {
        val sourceImageFile = "data/caffee/jeunehomme.jpg"
        val tfnetFile = "data/caffee/gender_net.caffemodel"
        val protoFil = "data/caffee/gender.prototxt"
        val labels = Arrays.asList("Male", "Female")

        runCaffeeNetwork(sourceImageFile, tfnetFile, protoFil, labels)
    }

    private fun runCaffeeNetwork(sourceImageFile: String, tfnetFile: String, protoFil: String, labels: List<*>) {
        val net = Dnn.readNetFromCaffe(protoFil, tfnetFile)
        val layernames = net.layerNames
        println(layernames)

        val image = Imgcodecs.imread(sourceImageFile)
        val inputBlob = Dnn.blobFromImage(image, 1.0, Size(256.0, 256.0), Scalar(0.0), true, true)
        net.setInput(inputBlob)
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV)
        val result = net.forward()

        println(result.dump())

        val minmax = Core.minMaxLoc(result)

        println(labels[minmax.maxLoc.x.toInt()])
    }

}
