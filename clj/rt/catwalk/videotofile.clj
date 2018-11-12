(ns rt.catwalk.videotofile
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.video :refer [new-videowrite]]
            [opencv4.core :refer [imread imwrite]])
  (:import (java.util Date)))

(defn -main [& args]
  (let [cap (new-videocapture "data/dnn/rt/cat.mp4")
        w (new-videowriter)
        buffer (new-mat (new-size 384 216) CV_8UC3)]
    (.open w (str "cat" (Date.) ".mpeg") -1 30 (new-size 384 216))
    (while (.read cap buffer)
      (let [annon (-> buffer (resize! (new-size 384 216)) (find-objects!))]
        (.write w annon)))
    (.release w)
    (.release cap)))
