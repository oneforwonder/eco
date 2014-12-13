(ns eco.ecology)

(def resources [:light
                :H20 :O2 :CO2 :NH3
                :carbs :proteins :fats])

(def organisms
  {:plants
    {:consumes [:H20 :NH3 :CO2]
     :produces [:carbs]
     :excretes [:O2]}

   :herbivores
    {:consumes [:carbs :O2 :H20]
     :produces [:fats :proteins]
     :excretes [:C02 :NH3]}

  :carnivores
    {:consumes [:proteins :fats :H20 :O2]
     :produces []
     :excretes [:C02 :NH3]}

  :decomposers
    {:consumes [:fats :proteins :carbs :02 :H20]
     :produces []
     :excretes [:C02 :NH3]}})


;;;; -----------------------------------------------------

(def organisms
  {:contents []
   :location [:x :y]
   :state :hungry})

(defn decompose-tile [tile]
  (let [in (get-in organisms :decomposers :consumes)
        out (get-in organisms :decomposers :excretes)]
    ;TODO
    ))
(defn decompose-world [world]
  (for [row world]
    (for [tile world]
      (decompose-tile tile))))

(defn update-plant [organism world]
    ;TODO
  )

(defn update-herbivore [organism world]
    ;TODO
  )

(defn update-carnivore [organism world]
    ;TODO
  )

(defn update-organism [organism world]
    ;TODO
  )
