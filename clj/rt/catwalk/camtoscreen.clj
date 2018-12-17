(ns rt.catwalk.camtoscreen
  (:require [rt.helpers :refer [find-objects!]]
            [opencv4.utils :refer [resize-by simple-cam-window]]))

(defn -main [& args]
  (simple-cam-window {:video {:device 0 :width 200 :height 220}}
                     (fn [buffer]
                       (-> buffer
                           (resize-by 0.25)
                           (find-objects!)))))


  (comment
  
    (simple-cam-window 
     {:video {:device 1 }}
     (fn [buffer] buffer))
  
  
    (require '[opencv4.core :refer :all])
    (defn annotate-red! [image detected-objects]
      (doseq [obj detected-objects]
        (put-text! image (str (:classid obj) "[" (:confidence obj) " %]") (:left-bottom obj) FONT_HERSHEY_TRIPLEX 2 (new-scalar 0 0 255))
        (rectangle image (:left-bottom obj) (:right-top obj) (new-scalar 0 0 255) 2))
      image)
  
    (simple-cam-window 
     {:frame {:width 700 :height 600} :video {:device 1 :width 1024 :height 768 }}
     (fn [buffer]
       (-> buffer
           (find-objects! annotate-red!))))
  
    )