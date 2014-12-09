(ns eco.terrain
  (:require [quil.core :as q]))

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
      (let [z (generate-depth (* 0.01 x) (* 0.01 y))]
        {:height z
         :material (if (< z 0.3) :water :dirt)
         :nutrients (rand-nutrients)
         :organisms []}))))


;;; Organisms

(def vivogenesis-chance 0.05)
(def death-chance 0.02)

(defn vivogenesis? []
  (< (rand) vivogenesis-chance))

(defn death? []
  (< (rand) death-chance))

(def material->organism
  {:dirt :grass
   :water :algae})

(defn rand-organism [tile]
  (material->organism (:material tile)))


;;;; Stepping

(defn step-organism [org]
  (if (death?) nil org))

(defn handle-tile-births [{:keys [material nutrients organisms] :as tile}]
  (if (and (not (empty? nutrients))
           (vivogenesis?))
    (assoc tile
           :nutrients (rest nutrients)
           :organisms (conj organisms (rand-organism tile)))
    tile))

(defn handle-tile-aging [{:keys [material nutrients organisms] :as tile}]
  (assoc tile :organisms (remove nil? (map step-organism organisms))))

(defn step-tile [tile]
  (-> tile
      (handle-tile-births)
      (handle-tile-aging)))

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

