package objectdetection.deepneuralnetwork;

import org.opencv.core.Point;

public class DnnObject {

    private int objectClassId;
    private String objectName;
    private Point leftBottom;
    private Point rightTop;
    private Point centerCoordinate;

    public DnnObject(int objectClassId, String objectName, Point leftBottom, Point rightTop, Point centerCoordinate) {
        this.objectClassId = objectClassId;
        this.objectName = objectName;
        this.leftBottom = leftBottom;
        this.rightTop = rightTop;
        this.centerCoordinate = centerCoordinate;
    }

    public int getObjectClassId() {
        return objectClassId;
    }

    public void setObjectClassId(int objectClassId) {
        this.objectClassId = objectClassId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Point getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(Point leftBottom) {
        this.leftBottom = leftBottom;
    }

    public Point getRightTop() {
        return rightTop;
    }

    public void setRightTop(Point rightTop) {
        this.rightTop = rightTop;
    }

    public Point getCenterCoordinate() {
        return centerCoordinate;
    }

    public void setCenterCoordinate(Point centerCoordinate) {
        this.centerCoordinate = centerCoordinate;
    }
}
