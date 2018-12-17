(ns rt.helpers
  (:require
    [opencv4.utils :as u]
    [opencv4.core :refer :all]))

(def net 
  (read-net-from-caffe "data/dnn/rt/deploy.prototxt" "data/dnn/rt/mobilenet_iter_73000.caffemodel"))

(def classes
  (line-seq (clojure.java.io/reader "data/dnn/rt/MobileNetSSD.names")))

(defn to-obj [reshaped i colsi rowsi]
  {:confidence
   (int (* 100 (nth (.get reshaped i 2) 0)))
   :classid
   (nth classes (nth (.get reshaped i 1) 0))
   :left-bottom
   (new-point (* colsi (nth (.get reshaped i 3) 0)) (* rowsi (nth (.get reshaped i 4) 0)))
   :right-top
   (new-point (* colsi (nth (.get reshaped i 5) 0)) (* rowsi (nth (.get reshaped i 6) 0)))})

(defn annotate-image! [image detected-objects]
  (doseq [obj detected-objects]
    (put-text! image (str (:classid obj) "[" (:confidence obj) " %]") (:left-bottom obj) FONT_HERSHEY_PLAIN 1 (new-scalar 255 0 0))
    (rectangle image (:left-bottom obj) (:right-top obj) (new-scalar 255 0 0) 1))
  image)

(defn find-objects! 
  ([image] (find-objects! image annotate-image!))
  ([image annotate_fn]
   (let [colsi (.cols image)
         rowsi (.rows image)
         meanVal 127.5
         blog (blob-from-image image 0.007843 (new-size 300 300) (new-scalar meanVal meanVal meanVal) false false)
         detections (do (.setInput net blog) (.forward net))
         reshaped (.reshape detections 1 (/ (.total detections) 7))
         detected-objects (map #(to-obj reshaped % colsi rowsi) (range 0 (.rows reshaped)))]
     (annotate_fn image detected-objects))))






  