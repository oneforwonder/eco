(ns eco.render
  (:require [quil.core :as q]
            [eco.terrain :refer [make-terrain]] 
            )
  )

(def tw 5)
(def th 5)

(def map-width   100)
(def map-height  60)

(def terrain (make-terrain map-width map-height))

(def color-map {:dirt [100 100 40] :water [0 0 128]})

(defn render-tile [ix iy tile]
  (apply q/fill (color-map (tile :material)))
  (q/rect (* tw ix) (* th iy) tw th))

(defn environment [state]
  (doseq [x (range  map-width)]
    (doseq [y (range map-height)]
      (render-tile x y (nth (nth (state :terrain) x) y)))))
