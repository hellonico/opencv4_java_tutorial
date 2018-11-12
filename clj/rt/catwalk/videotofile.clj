(ns rt.catwalk.videotofile
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.video :as v]
            [opencv4.core :refer [new-mat resize! new-size new-videocapture imread imwrite]])
  (:import (java.util Date)))

(defn -main [& args]
  (let [cap (new-videocapture "data/dnn/rt/cat.mp4")
        w (v/new-videowriter)
        buffer (new-mat)]
    (.open w (str "cat" (Date.) ".mpeg") -1 30 (new-size 384 216))
    (while (.read cap buffer)
      (let [annon (-> buffer (resize! (new-size 384 216)) (find-objects!))]
        (.write w annon)))
    (.release w)
    (.release cap)))
