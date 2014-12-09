(ns eco.terrain
  (:require [quil.core :as q]))

;;;; Variables

(def noise-scale 0.04)

;;;; Materials

(def materials [:dirt :water])

(defn rand-material []
  (rand-nth materials))


;;;; Nutrients

(def nutrients [:salt :nitrogen])

(defn rand-nutrient []
  (rand-nth nutrients))

(defn rand-nutrients []
  (let [n (rand-int (count nutrients))]
    (repeatedly n rand-nutrient)))


;;;; World

(defn generate-depth [x y]
  (try
    (q/noise x y)
    (catch Exception e (rand))))

(defn make-world [width height]
  (for [x (range width)]
    (for [y (range height)]
      (let [z (generate-depth (* noise-scale x) (* noise-scale y))]
        {:height z
         :material (if (< z 0.3) :water :dirt)
         :nutrients (rand-nutrients)
         :organisms []}))))


;;;; Stepping

(def vivogenesis-chance 0.05)

(defn vivogenesis? []
  (< (rand) vivogenesis-chance))

(def material->organism
  {:dirt :grass
   :water :algae})

(defn step-tile [{:keys [material nutrients organisms] :as tile}]
  (if (and (not (empty? nutrients))
           (vivogenesis?))
    (assoc tile
           :nutrients (rest nutrients)
           :organisms (conj organisms (material->organism material)))
    tile))

(defn step-world [world]
  (for [col world]
    (for [tile col]
      (step-tile tile))))


;;;; Text Display

(defn tile->letter [{:keys [material nutrients organisms] :as tile}]
  (let [primary (or (first organisms) material)]
    (get (name primary) 0)))

(defn print-world [world]
  (doseq [row world]
    (println (apply str (map tile->letter row)))))

(defn -main []
  (let [world (make-world 24 16)
        worlds (take 5 (iterate step-world world))]
    (println (nth worlds 4))))

