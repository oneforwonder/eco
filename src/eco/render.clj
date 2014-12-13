(ns eco.render
  (:require [quil.core :as q]))

;;;; Window variables

(def tw 4)
(def th 4)

(def map-width   200)
(def map-height  150)

(def window-w (* tw map-width))
(def window-h (* th map-height))

;;;; Nutrient variables

(def nd 2)

;;;; Organism variables

(def od 3)

;;;; Other variables

(def color-map {;All colors are in [h s b] or [h s b a]
        
                ;;Neutral
                :neutral [0 0 100 0.0]

                ;;Materials                
                :dirt  [36 50 50] 
                :water [240 80 100]
                
                ;;Nutrients
                :nitrogen [0 100 100 0.8]
                :salt     [45 100 100 0.8]

                ;;Organisms
                :grass [99 80 60 0.6]
                :algae [99 80 60 0.2]
                })

;;;; Terrain rendering functions

(defn terrain-color 
  "Change the color of the tile based on the height and material.
   Takes a tile map and returns a valid Quil color, currently [h s b a]"

  [{:keys [height material] :as tile}]

  (cond
    (< height 0.2) (conj (color-map material) 0.2)

    :else          (conj (color-map material) height)))

(defn render-terrain [ix iy tile]
  (apply q/fill (terrain-color tile))
  (q/rect (* tw ix) (* th iy) tw th))

;;;; Nutrient rendering functions

(defn nutrient-color [n]
  (color-map n))

(defn render-nutrients [ix iy tile]
  (apply q/fill (color-map :neutral))
  (doseq [n (tile :nutrients)]
    (apply q/fill (nutrient-color n))
    (q/rect (inc (* tw ix)) (inc (* th iy)) nd nd)))

;;;; Organism rendering functions

(defn organism-color
  "Similar to terrain-color.  Adds a color overlay to a tile given
  a specific organism."

  [o]

  (color-map o))

(defn render-organisms [ix iy tile]
  (apply q/fill (color-map :neutral))
  (doseq [o (tile :organisms)]
    (apply q/fill (organism-color o))
    (q/ellipse (* tw ix) (* th iy) tw th)))

;;;; Main rendering function - environment

(defn environment [{:keys [terrain organisms] :as world}]
  (doseq [x (range map-width)
          y (range map-height)]
    (render-terrain x y (terrain [x y]))
    ;(render-nutrients x y (nth (nth (state :terrain) x) y))
    ;(render-organisms x y (nth (nth (state :terrain) x) y))
      
    ))
