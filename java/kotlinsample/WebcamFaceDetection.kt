package kotlinsample

import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.CascadeClassifier
import org.opencv.videoio.VideoCapture
import org.scijava.nativelib.NativeLoader
import java.awt.Graphics
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import javax.swing.JFrame
import javax.swing.JPanel

internal class My_Panel : JPanel() {
    private var image: BufferedImage? = null

    /**
     * Converts/writes a Mat into a BufferedImage.
     *
     * @param matrix Mat of type CV_8UC3 or CV_8UC1
     * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
     */
    fun MatToBufferedImage(matBGR: Mat): Boolean {
        val startTime = System.nanoTime()
        val width = matBGR.width()
        val height = matBGR.height()
        val channels = matBGR.channels()
        val sourcePixels = ByteArray(width * height * channels)
        matBGR.get(0, 0, sourcePixels)
        // create new image and get reference to backing data
        image = BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR)
        val targetPixels = (image!!.raster.dataBuffer as DataBufferByte).data
        System.arraycopy(sourcePixels, 0, targetPixels, 0, sourcePixels.size)
        val endTime = System.nanoTime()
        println(String.format("Elapsed time: %.2f ms", (endTime - startTime).toFloat() / 1000000))
        return true
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)
        if (this.image == null)
            return
        g.drawImage(this.image, 10, 10, 2 * this.image!!.width, 2 * this.image!!.height, null)
        // g.drawString("This is my custom Panel!",10,20);
    }

    companion object {
        private val serialVersionUID = 1L
    }
}

internal class processor {
    private val face_cascade: CascadeClassifier

    // Create a constructor method
    init {
        face_cascade = CascadeClassifier("data/haarcascades/haarcascade_frontalface_alt.xml")
        if (face_cascade.empty()) {
            println("--(!)Error loading A\n")
        } else {
            println("Face classifier loooaaaaaded up")
        }
    }

    fun detect(inputframe: Mat): Mat {
        val startTime = System.nanoTime()
        val mRgba = Mat()
        val mGrey = Mat()
        val faces = MatOfRect()
        inputframe.copyTo(mRgba)
        inputframe.copyTo(mGrey)
        Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY)
        Imgproc.equalizeHist(mGrey, mGrey)
        face_cascade.detectMultiScale(mGrey, faces)
        val endTime = System.nanoTime()
        println(String.format("Detect time: %.2f ms", (endTime - startTime).toFloat() / 1000000))
        println(String.format("Detected %s faces", faces.toArray().size))
        for (rect in faces.toArray()) {
            val center = Point(rect.x + rect.width * 0.5, rect.y + rect.height * 0.5)
            Imgproc.ellipse(mRgba, center, Size(rect.width * 0.5, rect.height * 0.5), 0.0, 0.0, 360.0,
                    Scalar(255.0, 0.0, 255.0), 4, 8, 0)

        }
        return mRgba
    }
}

object FaceDetection {

    @JvmStatic
    fun main(arg: Array<String>) {
        NativeLoader.loadLibrary(Core.NATIVE_LIBRARY_NAME)
        val window_name = "Capture - Face detection"
        val frame = JFrame(window_name)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(400, 300)
        val my_processor = processor()
        val my_panel = My_Panel()
        frame.contentPane = my_panel
        frame.isVisible = true
        // -- 2. Read the video stream
        var webcam_image = Mat()
        val capture = VideoCapture(0)

        // capture.set(Video., value)
        if (capture.isOpened) {

            while (true) {
                capture.read(webcam_image)

                // Mat resizeimage = new Mat();
                val sz = Size(300.0, 180.0)
                Imgproc.resize(webcam_image, webcam_image, sz)

                if (!webcam_image.empty()) {
                    frame.setSize(2 * webcam_image.width() + 40, 2 * webcam_image.height() + 60)
                    // -- 3. Apply the classifier to the captured image
                    webcam_image = my_processor.detect(webcam_image)

                    // -- 4. Display the image
                    my_panel.MatToBufferedImage(webcam_image) // We could look at the error...
                    my_panel.repaint()
                } else {
                    println(" --(!) No captured frame -- Break!")
                    break
                }
            }
        }
        return
    }
}