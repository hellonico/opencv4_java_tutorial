(ns rt.catwalk.videotoscreen
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.utils :refer [show re-show]]
            [opencv4.core :refer [imread imwrite]]))


(defn -main [& args]
  (let [
        cap (new-videocapture "data/dnn/rt/cat.mp4")
        buffer (new-mat)
        frame (show (do (.read cap buffer) (resize! buffer (new-size 384 216))))]
    (while (not (nil? buffer))
      (.read cap buffer)
      (let [annon (-> buffer (resize! (new-size 384 216)) (find-objects!))]
        (re-show frame annon)))
    (.release cap)))
