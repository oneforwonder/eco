(ns eco.terrain
  (:require [quil.core :as q]))

;;;; Materials

(def materials [:dirt :water])

(defn rand-material []
  (rand-nth materials))

;; Given dimensions in tiles, generates a terrain map (2D array) of materials.
(defn make-terrain [width height]
  (repeatedly width #(repeatedly height rand-material)))


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
      (let [z (generate-depth x y)]
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
           :nutrients (pop nutrients)
           :organisms (conj organisms (material->organism material))))
    tile)

(defn step-world [world]
  (for [col world]
    (for [tile col]
      (step-tile tile))))


;;;; Text Display

;(defn print-terrain [terra]
  ;(doseq [row terra]
    ;(println (apply str (map {:dirt \d :water \w} row)))))

;(defn -main []
  ;(println (make-terrain 24 16)))

(defn tile->letter [{:keys [material nutrients organisms] :as tile}]
  (let [primary (or (first organisms) material)]
    (get primary 0)))

(defn print-world [world]
  (doseq [row world]
    (println (apply str (map tile->letter row)))))

(defn -main []
  (println (make-world 24 16))
  ;(let [world (make-world 24 16)
        ;worlds (take 5 (iterate step-world world))]
    ;(doseq [w worlds]
      ;(print-world w)))
  )
