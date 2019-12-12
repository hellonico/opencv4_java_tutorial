package me;

import origami.Filter;
import org.opencv.core.Core;
import org.opencv.core.Mat;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Compare implements Filter {
    Function<Mat,Mat> filter = mat -> mat;

    public Compare(Function<Mat,Mat> filter) {
        this.filter = filter;
    }

    @Override
    public Mat apply(Mat matFrame) {
        List<Mat> mats = Arrays.asList(matFrame.clone(), filter.apply(matFrame));
        Mat result = new Mat();
        Core.hconcat(mats, result);
        return result;
    }
}
