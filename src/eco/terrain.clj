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

