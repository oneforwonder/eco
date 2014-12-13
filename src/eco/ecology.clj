(ns eco.ecology)

(def resources [:H2O :O2 :CO2 :NH3
                :carbs :proteins :fats])

(def organisms
  {:plants
    {:consumes [:H2O :NH3 :CO2]
     :produces [:carbs]
     :excretes [:O2]}

   :herbivores
    {:consumes [:carbs :O2 :H2O]
     :produces [:fats :proteins]
     :excretes [:CO2 :NH3]}

  :carnivores
    {:consumes [:proteins :fats :H2O :O2]
     :produces []
     :excretes [:CO2 :NH3]}

  :decomposers
    {:consumes [:fats :proteins :carbs :O2 :H2O]
     :produces []
     :excretes [:CO2 :NH3]}})


;;;; -----------------------------------------------------

;(def organisms
  ;{:contents []
   ;:location [:x :y]
   ;:state :hungry})

;(defn decompose-tile [tile]
  ;(let [in (get-in organisms :decomposers :consumes)
        ;out (get-in organisms :decomposers :excretes)]
    ;;TODO
    ;))
;(defn decompose-world [world]
  ;(for [row world]
    ;(for [tile world]
      ;(decompose-tile tile))))

;(defn update-plant [organism world]
    ;;TODO
  ;)

;(defn update-herbivore [organism world]
    ;;TODO
  ;)

;(defn update-carnivore [organism world]
    ;;TODO
  ;)

;(defn update-organism [organism world]
    ;;TODO
  ;)
