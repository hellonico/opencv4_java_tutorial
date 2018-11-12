(ns caffee2
  (:require
    [clojure.pprint :refer [pprint]]
    [opencv4.utils :as u]
    [opencv4.core :refer :all]))

(def net (read-net-from-caffe "data/dnn/rt/deploy.prototxt" "data/dnn/rt/mobilenet_iter_73000.caffemodel"))
(def inWidth 300)
(def inHeight 300)
(def inScaleFactor 0.007843)
(def thresholdDnn 0.6)
(def meanVal 127.5)

(def image (imread "data/dnn/rt/dog.jpg"))
(def colsi (.cols image))
(def rowsi (.rows image))

(def blog (blob-from-image image inScaleFactor (new-size inWidth inHeight) (new-scalar meanVal meanVal meanVal) false false))
(.setInput net blog)
(def detections (.forward net))
(def reshaped (.reshape detections 1 (/ (.total detections) 7)))

(def classes (line-seq (clojure.java.io/reader "data/dnn/rt/MobileNetSSD.names")))
(defn to-obj [reshaped i]
  {:confidence
   (nth (.get reshaped i 2) 0)
   :classid
   (nth classes (nth (.get reshaped i 1) 0))
   :left-bottom
   (new-point (* colsi (nth (.get reshaped i 3) 0)) (* rowsi (nth (.get reshaped i 4) 0)))
   :right-top
   (new-point  (* colsi (nth (.get reshaped i 5) 0)) (* rowsi (nth (.get reshaped i 6) 0))) })

(def detected-objects (map #(to-obj reshaped %) (range 0 (.rows reshaped))))

(doall
  (doseq [obj detected-objects]
    (println (str (:classid obj)))
    (put-text! image (str (:classid obj)) (:left-bottom obj) FONT_HERSHEY_PLAIN 1 (new-scalar 255 0 0))
    (rectangle image (:left-bottom obj) (:right-top obj) (new-scalar 255 0 0) 1)))

  (imwrite image "detected.jpg" )
