(ns eco.terrain)

(def materials [:dirt :water])

(defn rand-material []
  (rand-nth materials))

;; Given dimensions in tiles, generates a terrain map (2D array) of materials.
(defn make-terrain [width height]
  (repeatedly width #(repeatedly height rand-material)))

(defn print-terrain [terra]
  (doseq [row terra]
    (println (apply str (map {:dirt \d :water \w} row)))))

(defn -main []
  (println (make-terrain 24 16)))

;----------------------------

(def nutrients [:salt :nitrogen])

(defn rand-nutrient []
  (rand-nth nutrients))

(defn rand-nutrients []
  (let [n (rand-int (count nutrients))]
    (repeatedly n rand-nutrient)))

(defn make-world [width height]
  (for [_ (range width)]
    (for [_ (range height)]
      {:material (rand-material)
       :nutrients (rand-nutrients)
       :organisms []})))

;----------------------------

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

(defn step-world [terra]
  (map step-tile terra))

