(ns eco.organism
  (:require [eco.ecology :as e]
            [eco.world   :as w]
            [clojure.set :refer (intersection)]
            ))

(defn make-organism [kind]
  (let [o (e/organisms kind)]
    
    (assoc (e/organisms kind)
        
           :id (gensym "Organism ")

           ;;;; Metabolic information
           :input-storage   (zipmap (o :consumes) (repeat (count (o :consumes)) 0))
           :product-storage (zipmap (o :produces) (repeat (count (o :produces)) 0))
           :m-rate          0.1 
           :dead?           false

           ;;;; Behavior state
           :x               0
           :y               0
           :mood            :none
           :movements       {:default (fn [args] [0 0])}
           
           ;;;; Behavior thresholds
           :death           5
           :starving        10
           :hungry          15

           )))

(defn map-vals 
  "Updates the values of specific keys in a map if given, all keys otherwise, using provided function."

  ([f m]
   (map-vals f m (keys m)))
  
  ([f m ks]
   (reduce (fn [m* k] (update-in m* [k] f)) m ks)))

(defn metabolize 
  "Metabolism is defined as the conversion of one of each input into one of each product."

  [organism]
  
  (let [i (organism :input-storage)
        p (organism :product-storage)]

    (assoc organism :input-storage   (map-vals dec i)
                    :product-storage (map-vals inc p))))

(defn dead? 
  "Check for death last, since you want to give the organism an opportunity to metabolize first."
  
  [organism]

  (if (<= (reduce max (vals (organism :product-storage))) (organism :death))
    (assoc organism :dead? true)
    (assoc organism :dead? false)))

(defn update-organism
  "A massive clusterfunction.  Updates the world, one organism at a time."

  [org-id world]

  (let [organisms (world :organisms)
        org       (first (filter (fn [org] (= org-id (org :id))) organisms))
        others    (remove (fn [org] (= org-id (org :id))) organisms)
        
        x         (org :x)
        y         (org :y)
        available (w/get-combined-nutrients world [x y])    
        
        intake    (map-vals (fn [n] (min n 1)) available)
        intake*   (remove (fn [kv] (= 0 (val kv))) intake)
        resses    (set (map first intake*))
    
        i         (org :input-storage)
        i-ks      (set (keys i))

        intook    (intersection resses i-ks)

        org*      (assoc org :input-storage (map-vals inc i intook))
        org**     (if (< (rand) (org :m-rate)) (dead? (metabolize org*)) (dead? org*))

        world*    (assoc world :organisms (conj others org**))]

    (w/update-combined-nutrients world* [x y] intook dec)))

