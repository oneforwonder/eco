(ns eco.world
  (:require [quil.core :as q]))

;;;; Generate World

(def noise-scale 0.04)
(def terrain-materials [:dirt :water])
(def terrain-nutrients [:H20 :NH3])
(def world-nutrients [:O2 :C02])
(def min-nutrient 20)
(def max-nutrient 200)

(defn rand-nutrient-amount []
  (+ min-nutrient (rand-int (- max-nutrient min-nutrient))))

(defn generate-depth [x y]
  (try
    (q/noise (* noise-scale x) (* noise-scale y))
    (catch Exception e (rand))))

(defn generate-tile [x y]
  (let [z (generate-depth x y)]
    {:material (if (< z 0.3) :water :dirt)
     :height z
     :nutrients {:H20 (rand-nutrient-amount)
                 :NH3 (rand-nutrient-amount)}}))

(defn generate-terrain [width height]
  (reduce conj
    (for [x (range width)
          y (range height)]
      {[x y] (generate-tile x y)})))

(defn generate-world [width height]
  {:terrain (generate-terrain width height)
   :air {:O2  (rand-nutrient-amount)
         :CO2 (rand-nutrient-amount)}
   :time 0
   :organisms {}})


;;;; Update World

(defn update-time [world]
  (assoc world :time (mod (inc (:time world)) 24)))

(defn update-weather [world]
  ;; TODO: Sometimes rain here.
  world)

(defn update-organisms [world]
  ;; TODO: Do many things here.
  world)

(defn update-world [world]
  (-> world
      update-time
      update-weather
      update-organisms))

