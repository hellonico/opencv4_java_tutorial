package me;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import origami.Camera;
import origami.Filter;
import origami.Filters;
import origami.Origami;
import origami.filters.FPS;
import origami.filters.detect.Yolo;

import java.util.List;

public class YoloAgain {
    static class MyYolo extends Yolo {
        Scalar color = new Scalar(110.0D, 220.0D, 0.0D);
        String only = null;
        boolean annotateWithTotal = false;

        MyYolo(String spec) {
            super(spec);
        }

        public MyYolo annotateWithTotal() {
            this.annotateWithTotal = true;
            return this;
        }

        public MyYolo only(String only) {
            this.only = only;
            return this;
        }

        public MyYolo color(Scalar color) {
            this.color = color;
            return this;
        }

        @Override
        public void annotateAll(Mat frame, List<List> results) {
            if (only == null) {
                if (annotateWithTotal)
                    annotateWithCount(frame, results.size());
                else
                    super.annotateAll(frame, results);
            } else {
                if (!annotateWithTotal)
                    results.stream().filter(result -> result.get(1).equals(only)).forEach(r -> {
                        annotateOne(frame, (Rect) r.get(0), (String) r.get(1));
                    });
                else
                    annotateWithCount(frame, (int) results.stream().filter(result -> result.get(1).equals(only)).count());
            }
        }

        public void annotateWithCount(Mat frame, int count) {
            Imgproc.putText(frame, (only == null ? "ALL" : only) + " (" + count + ")", new Point(50, 500), 1, 4.0D, color, 3);
        }

        public void annotateOne(Mat frame, Rect box, String label) {
            if (only == null || only.equals(label)) {
                Imgproc.putText(frame, label, new Point(box.x, box.y), 1, 3.0D, color, 3);
                Imgproc.rectangle(frame, box, color, 2);
            }
        }
    }

    public static void main(String[] args) {
        Origami.init();
        String video = "/Users/niko/Dropbox/MeiMa+photo/meimanon2019/597842788.852328.mp4";
//        Filter filter = new Filters(new MyYolo("networks.yolo:yolov3-tiny:1.0.0").only("car"), new FPS());
        MyYolo yolo = new MyYolo("networks.yolo:yolov3-tiny:1.0.0"); //.only("person");
        yolo.thresholds(0.4f, 1.0f);
        yolo.annotateWithTotal();

        Filter filter = new Filters(yolo, new FPS());
        new Thread(() -> {
            new Camera().device(video).filter(filter).run();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                System.out.println("only cat");
                yolo.only("cat");
                Thread.sleep(5000);
                System.out.println("only person");
                yolo.only("person");
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }).start();

    }
}
