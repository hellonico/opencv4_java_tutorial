package dnn.rt;

import org.opencv.core.Point;

class DnnObject {

    int objectClassId;
    String objectName;
    Point leftBottom;
    Point rightTop;
//    Point centerCoordinate;
    double confidence;
}
