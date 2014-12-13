(ns eco.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            
            [eco.world :refer [generate-world update-world]]
            [eco.render :as r]))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  (q/background 0)
  (q/ellipse-mode :corner)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb 360 100 100 1.0)
  ; setup function returns initial state. It contains
  ; circle color and position.
  (q/no-stroke)
  (generate-world r/map-width r/map-height))

(defn update [state]
  ; Update sketch state by changing circle color and position.
  (q/background 0)
  (update-world state))

;(defn draw [state]
  ;; Clear the sketch by filling it with light-grey color.
  ;(q/background 240)
  ;; Set circle color.
  ;(q/fill (:color state) 255 255)
  ;; Calculate x and y coordinates of the circle.
  ;(let [angle (:angle state)
        ;x (* 150 (q/cos angle))
        ;y (* 150 (q/sin angle))]
    ;; Move origin point to the center of the sketch.
    ;(q/with-translation [(/ (q/width) 2)
                         ;(/ (q/height) 2)]
      ;; Draw the circle.
      ;(q/ellipse x y 100 100))))

(q/defsketch eco
  :title "This... is Eco."
  :size [r/window-w r/window-h]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update is called on each iteration before draw is called.
  ; It updates sketch state.
  :update update
  :draw r/environment
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
