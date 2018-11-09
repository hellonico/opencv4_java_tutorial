import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfFloat;
import org.opencv.dnn.DictValue;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.sun.tools.internal.ws.wsdl.parser.Util.fail;

public class TensorFlowTest {

    private final static String ENV_OPENCV_DNN_TEST_DATA_PATH = "OPENCV_DNN_TEST_DATA_PATH";

    private final static String ENV_OPENCV_TEST_DATA_PATH = "OPENCV_TEST_DATA_PATH";

    static String modelFileName = "";
    static String sourceImageFile = "";

    static Net net;

    private static void normAssert(Mat ref, Mat test) {
        final double l1 = 1e-5;
        final double lInf = 1e-4;
        double normL1 = Core.norm(ref, test, Core.NORM_L1) / ref.total();
        double normLInf = Core.norm(ref, test, Core.NORM_INF) / ref.total();
//        assertTrue(normL1 < l1);
//        assertTrue(normLInf < lInf);
    }

    public static void main(String[] args) throws Exception {

        String envDnnTestDataPath = "" ; //System.getenv(ENV_OPENCV_DNN_TEST_DATA_PATH);

        if(envDnnTestDataPath == null){
//            isTestCaseEnabled = false;
            return;
        }

        File dnnTestDataPath = new File(envDnnTestDataPath);
        modelFileName =  new File(dnnTestDataPath, "data/tf/tensorflow_inception_graph.pb").toString();

        String envTestDataPath = System.getenv(ENV_OPENCV_TEST_DATA_PATH);

//        if(envTestDataPath == null) throw new Exception(ENV_OPENCV_TEST_DATA_PATH + " has to be defined!");

//        File testDataPath = new File(envTestDataPath);
        File testDataPath = new File("./");

        File f = new File(testDataPath, "data/tf/grace_hopper_227.png");
        sourceImageFile = f.toString();
        if(!f.exists()) throw new Exception("Test image is missing: " + sourceImageFile);

        net = Dnn.readNetFromTensorflow(modelFileName, null);
//        Dnn.readNetFrom
    }

//
//    public void testGetLayerTypes() {
//        List<String> layertypes = new ArrayList();
//        net.getLayerTypes(layertypes);
//
//        assertFalse("No layer types returned!", layertypes.isEmpty());
//    }
//
//    public void testGetLayer() {
//        List<String> layernames = net.getLayerNames();
//
//        assertFalse("Test net returned no layers!", layernames.isEmpty());
//
//        String testLayerName = layernames.get(0);
//
//        DictValue layerId = new DictValue(testLayerName);
//
//        assertEquals("DictValue did not return the string, which was used in constructor!", testLayerName, layerId.getStringValue());
//
//        Layer layer = net.getLayer(layerId);
//
//        assertEquals("Layer name does not match the expected value!", testLayerName, layer.get_name());
//
//    }
//
//    public void checkInceptionNet(Net net)
//    {
//        Mat image = Imgcodecs.imread(sourceImageFile);
//        assertNotNull("Loading image from file failed!", image);
//
//        Mat inputBlob = Dnn.blobFromImage(image, 1.0, new Size(224, 224), new Scalar(0), true, true);
//        assertNotNull("Converting image to blob failed!", inputBlob);
//
//        net.setInput(inputBlob, "input");
//
//        Mat result = new Mat();
//        try {
//            net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
//            result = net.forward("softmax2");
//        }
//        catch (Exception e) {
//            fail("DNN forward failed: " + e.getMessage());
//        }
//        assertNotNull("Net returned no result!", result);
//
//        result = result.reshape(1, 1);
//        Core.MinMaxLocResult minmax = Core.minMaxLoc(result);
//        assertEquals("Wrong prediction", (int)minmax.maxLoc.x, 866);
//
//        Mat top5RefScores = new MatOfFloat(new float[] {
//                0.63032645f, 0.2561979f, 0.032181446f, 0.015721032f, 0.014785315f
//        }).reshape(1, 1);
//
//        Core.sort(result, result, Core.SORT_DESCENDING);
//
//        normAssert(result.colRange(0, 5), top5RefScores);
//    }
//
//    public void testTestNetForward() {
//        checkInceptionNet(net);
//    }
//
//    public void testReadFromBuffer() {
//        File modelFile = new File(modelFileName);
//        byte[] modelBuffer = new byte[ (int)modelFile.length() ];
//
//        try {
//            FileInputStream fis = new FileInputStream(modelFile);
//            fis.read(modelBuffer);
//            fis.close();
//        } catch (IOException e) {
//            fail("Failed to read a model: " + e.getMessage());
//        }
//        net = Dnn.readNetFromTensorflow(new MatOfByte(modelBuffer));
//        checkInceptionNet(net);
//    }
}

