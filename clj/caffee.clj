(ns caffee
  (:require
    [clojure.pprint :refer [pprint]]
    [opencv4.utils :as u]
    [opencv4.core :refer :all])
  (:import (org.opencv.dnn Dnn)))

(defn run-image-on-network[sourceImageFile tffile protFil labels]
(let [ net (Dnn/readNetFromCaffe protFil tffile)
       image (imread sourceImageFile)
      inputBlob (blob-from-image image 1.0 (new-size 256 256))
      ]
    (doto net
     (.setInput inputBlob)
     (.setPreferableBackend DNN_BACKEND_OPENCV))

    (let [
          result
          (->
            (.forward net)
            (.reshape 1 1))
          minmax (min-max-loc result)
          x (-> minmax (.-maxLoc) (.-x) (int))
          ]
    (println (nth labels x))
    (pprint  (dump result)))))

(defn -main [ & args]
  (let [
        tfnetFile  "data/caffee/gender_net.caffemodel"
        protoFil  "data/caffee/gender.prototxt"
        image "data/caffee/teenager2.jpg"
        labels ["Male" "Female"]
        ]
  (run-image-on-network
    image
    tfnetFile
    protoFil
    labels
    )))

(comment
  (-main)
  )