(ns rt.catwalk.oneimage
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.core :refer [imread imwrite]]))

(defn -main [& args]
  (-> (or (first args) "data/dnn/rt/catwalk.jpg")
      (imread)
      (find-objects!)
      (imwrite "detected.jpg")))
