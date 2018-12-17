(ns rt.cam1toscreen
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.core :refer :all]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn annotate-red! [image detected-objects]
  (put-text! image (str "Detecting [" (count detected-objects) "]" ) (new-point 10 10)  FONT_HERSHEY_PLAIN 1 (new-scalar 0 0 0 ))
  (doseq [obj detected-objects]
    (put-text! image (str (:classid obj) "[" (:confidence obj) " %]") (:left-bottom obj) FONT_HERSHEY_TRIPLEX 1 (new-scalar 0 0 255))
    (rectangle image (:left-bottom obj) (:right-top obj) (new-scalar 0 0 255) 1))
  image)

(defn -main [& args]
    (simple-cam-window
     {:frame {:width 700 :height 600} :video {:device 1 :width 2048 :height 1536}}
     (fn [buffer]
       (-> buffer (resize-by 0.3) (find-objects! annotate-red!)))))