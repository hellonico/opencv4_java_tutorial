(ns sepia
  (:require
    [opencv4.utils :as u]
    [opencv4.core :refer :all]))

(-> "https://raw.githubusercontent.com/hellonico/origami/master/doc/lena.png"
  (u/mat-from-url)
  (transform!
    (u/matrix-to-mat [
      [0.272 0.534 0.131]
      [0.349 0.686 0.168]
      [0.393 0.769 0.189]]))
      (u/imshow {:frame {:width 800 :height 600}}))