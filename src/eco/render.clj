(ns eco.render
  (:require [quil.core :as q]
            [eco.terrain :refer [make-terrain]]))

;;;; Terrain rendering variables

(def tw 4)
(def th 4)

(def map-width   200)
(def map-height  150)

(def window-w (* tw map-width))
(def window-h (* th map-height))

(def color-map {;All colors are in [h s b] or [h s b a]
                
                ;;Materials                
                :dirt  [36 50 50] 
                :water [240 80 100]
                
                ;;Organisms
                :grass [99 60 60 0.6]
                :algae [99 60 60 0.2]
                })

;;;; Terrain rendering functions

(defn terrain-color 
  "Change the color of the tile based on the height and material.
   Takes a tile map and returns a valid Quil color, currently [h s b a]"

  [{:keys [height material] :as tile}]

  (cond
    (< height 0.2) (conj (color-map material) 0.2)

    :else          (conj (color-map material) height)))

  ;(conj (color-map (tile :material)) (max 0.2 (tile :height))))

(defn organism-color
  "Similar to terrain-color.  Adds a color overlay to a tile given
  a specific organism."

  [{:keys [organism] :as tile}]
  (color-map organism))

(defn render-terrain [ix iy tile]
  (apply q/fill (terrain-color tile))
  (q/rect (* tw ix) (* th iy) tw th))

(defn render-organisms [ix iy tile]
  (q/fill 0 0 100 0.0)
  (doseq [o (tile :organisms)]
    (apply q/fill (organism-color o))
    (q/rect (* tw ix) (* th iy) tw th)))

(defn environment [state]
  (doseq [x (range map-width)]
    (doseq [y (range map-height)]
      (render-terrain x y (nth (nth (state :terrain) x) y))
      (render-organisms x y (nth (nth (state :terrain) x) y)))))
