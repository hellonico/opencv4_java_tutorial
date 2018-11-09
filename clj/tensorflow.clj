(ns tensorflow
    (:import 
      [org.opencv.core Core]
      [org.opencv.dnn Dnn])
    (:require
      [clojure.pprint :refer [pprint]]
      [opencv4.utils :as u]
      [opencv4.core :refer :all]))

(defn run-image-on-network[sourceImageFile tffile]
(let [ net (Dnn/readNetFromTensorflow tffile)
       image (imread sourceImageFile)
      inputBlob (Dnn/blobFromImage image 1.0 (new-size 224 224) (new-scalar 0.0) true true)]
    (doto net
     (.setInput inputBlob "input")
     (.setPreferableBackend Dnn/DNN_BACKEND_OPENCV))

    (pprint
      (-> 
      (.forward net "softmax2")
      (.reshape 1 1)
      ( #(do (Core/minMaxLoc %) %) )
      (sort! SORT_DESCENDING)
      (.colRange 0 5)
      (dump)))))

(defn -main [ & args] 
  (run-image-on-network 
    "data/tf/grace_hopper_227.png"
    "data/tf/tensorflow_inception_graph.pb"))