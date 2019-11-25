import picocli.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;

import static org.opencv.core.Core.NATIVE_LIBRARY_NAME;
import static org.scijava.nativelib.NativeLoader.loadLibrary;

public class CVApp {

    public static void main(String... args) throws Exception {
        loadLibrary(NATIVE_LIBRARY_NAME);
        ArrayList<String> params = new ArrayList<String>(Arrays.asList(args));
        String className = params.remove(0);
        new CommandLine(Class.forName(className).newInstance()).execute(params.toArray(new String[params.size()]));
    }
}
