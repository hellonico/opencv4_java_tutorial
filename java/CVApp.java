import origami.Origami;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;

import static org.opencv.core.Core.NATIVE_LIBRARY_NAME;
import static org.scijava.nativelib.NativeLoader.loadLibrary;

public class CVApp {

    public static void main(String... args) throws Exception {
        Origami.init();
        ArrayList<String> params = new ArrayList<String>(Arrays.asList(args));
        String className = params.remove(0);
        System.out.println(String.format("Starting App:\t%s", className));
        new CommandLine(Class.forName(className).newInstance()).execute(params.toArray(new String[params.size()]));
        System.out.println("\ud83d\udc3b");
        System.exit(0);
    }
}
