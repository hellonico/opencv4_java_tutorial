(ns rt.catwalk.videotoscreen
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.utils :refer [show re-show]]
            [opencv4.core :refer [new-mat resize! new-size new-videocapture imread imwrite]]))

(defn -main [& args]
  (let [
        cap (new-videocapture (or (first args) "data/dnn/rt/cat.mp4"))
        buffer (new-mat)
        frame (show (do (.read cap buffer) (resize! buffer (new-size 384 216))))]
    (while (.read cap buffer)
      (let [annon (-> buffer (resize! (new-size 384 216)) (find-objects!))]
        (re-show frame annon)))
    (.release cap)))
