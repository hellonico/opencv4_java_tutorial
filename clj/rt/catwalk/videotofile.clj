(ns rt.catwalk.videotofile
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.utils :refer [show re-show]]
            [opencv4.video :refer [new-videowriter]]
            [opencv4.core :refer [new-mat resize! new-size new-videocapture imread imwrite]])
  (:import (java.util Date)))

(defn -main [& args]
  (let [
        cap (new-videocapture (or (first args) "data/dnn/rt/cat.mp4"))
        buffer (new-mat)
        output-file (or (second args) (str "cat_" (Date.) ".mpeg"))
        w (new-videowriter)
        ; need a fake frame before doing analysis probably due to the empty mat above
        _frame (do (.read cap buffer) (resize! buffer (new-size 384 216)))]
    (.open w output-file -1 30 (new-size 384 216))
    (while (.read cap buffer)
      (let [annon (-> buffer (resize! (new-size 384 216)) (find-objects!))]
        (.write w annon)))
        ;(re-show frame annon)))
    (.release w)
    (.release cap)))