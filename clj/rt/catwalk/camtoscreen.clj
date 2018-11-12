(ns rt.catwalk.camtoscreen
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
	(simple-cam-window 
  (fn [buffer]
    (-> buffer
        (resize-by 0.25)
        (find-objects!)))))