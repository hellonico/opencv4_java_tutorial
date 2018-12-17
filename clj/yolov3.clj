(ns yolov3
  (:require [opencv4.core :refer :all]
            [opencv4.dnn :as dnn]
            [opencv4.utils :as u]))

(defn- indexes-of-nms-boxes[locations confidences confidence-threshold nms-threshold]
  (let[locMat (new-matofrect) confidenceMat (new-matoffloat) indexMat (new-matofint)]
    (.fromList locMat locations)
    (.fromList confidenceMat confidences)
    (dnn/nms-boxes locMat confidenceMat (float confidence-threshold) (float nms-threshold) indexMat)
    indexMat
    (into-array (map #(nth (.get indexMat % 0) 0) (range (.total indexMat))))))

(defn- nms-filtered-objects[classes locations confidences ]
  (let [indexes (indexes-of-nms-boxes locations confidences 0.1 0.1)]
    (map
      #(hash-map :confidence (nth confidences %) :label (nth classes %) :box (nth locations %))
      indexes)))

(defn run-inference-on-image[_image net ]
  (let[layers (dnn/output-layers net)
       outputs (new-arraylist (map (fn [_] (new-mat)) (range (count layers))))
       blob (dnn/blob-from-image _image 0.00392157 (new-size 416 416) (new-scalar 0 0 0) false)
       tmpLocations (new-arraylist)
       tmpClasses (new-arraylist)
       tmpConfidences (new-arraylist)
       w (.width _image)
       h (.height _image)]
    (.setInput net blob)
    (.forward net outputs layers)

    (doseq [out outputs]
      (dotimes [j (.height out)]
        (let [ row (.row out j) scores (.colRange row 5 (.width out)) result (min-max-loc scores)]
          (if (> (.-maxVal result) 0)
            (let [one (zipmap #{:center-x :center-y :width :height} (map #(nth (.get row 0 %) 0) (range 4)))
                  rect (new-rect
                         (- (* w (:center-x one)) (/ (* w (:width one)) 2))
                         (- (* h (:center-y one)) (/ (* h (:height one)) 2))
                         (* w (:width one))
                         (* h (:height one)))]
              (.add tmpConfidences (-> result (.-maxVal) float))
              (.add tmpLocations rect)
              (.add tmpClasses (-> result (.-maxLoc) (.-x) int)))))))
    [_image (nms-filtered-objects tmpClasses tmpLocations tmpConfidences)]))