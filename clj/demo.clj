(ns demo
                                      (:require [opencv4.core :refer :all]
                                                [opencv4.dnn :as dnn]
                                                [yolov3]))

(defn draw-boxes![[ img first detected second] labels]
  (doseq [{confidence :confidence label :label box :box} detected]
    (rectangle img box (new-scalar 0 0 0) 2)
    (put-text! img
               (str (nth labels label) "[" (int (* 100 confidence)) "% ]")
               (new-point (double (.-x box)) (double (.-y box)))
               FONT_HERSHEY_PLAIN 1.0 (new-scalar 0 0 0 ) 2))
  img)

(def net
  (dnn/read-net-from-darknet "data/yolov3/yolov3.cfg" "data/yolov3/yolov3.weights"))
(def labels
  (line-seq (clojure.java.io/reader "data/yolov3/coco.names")))

(time
  (-> "data/yolov3/catwalk.jpg"
      (imread)
      (yolov3/run-inference-on-image net)
      (draw-boxes! labels)
      (imwrite "cat_output.jpg")))