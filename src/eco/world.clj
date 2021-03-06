(ns eco.world
  (:require [quil.core :as q]))

;;;; Generate World

(def noise-scale 0.04)
(def terrain-materials [:dirt :water])
(def terrain-nutrients [:H2O :NH3])
(def world-nutrients [:O2 :CO2])
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
     :nutrients {:H2O (rand-nutrient-amount)
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


;;;; Get/Set World Conviently

(defn get-combined-nutrients [world coord]
  (conj (get world :air)
        (get-in world [:terrain coord :nutrients])))

(defn set-combined-nutrients [world coord nutrients]
  (let [combined (merge (get-combined-nutrients) nutrients)]
    (-> world
      (assoc-in [:air :CO2] (combined :CO2))
      (assoc-in [:air :O2]  (combined :O2))
      (assoc-in [:terrain coord :nutrients :NH3] (combined :NH3))
      (assoc-in [:terrain coord :nutrients :H2O] (combined :H2O)))))

(defn update-combined-nutrients [world coord nutrients f]
  (let [nu-fn (fn [n] (if (contains? (set nutrients) n) f identity))]
    (-> world
      (update-in [:air :CO2] (nu-fn :CO2))
      (update-in [:air :O2]  (nu-fn :O2))
      (update-in [:terrain coord :nutrients :NH3] (nu-fn :NH3))
      (update-in [:terrain coord :nutrients :H2O] (nu-fn :H2O)))))


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

