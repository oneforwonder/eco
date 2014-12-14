(ns eco.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            
            [eco.world :refer [generate-world update-world]]
            [eco.render :as r]))

(defn setup []
  ;;;; Set maximum frame rate to 30 frames per second.
  (q/frame-rate 30)
  
  ;;;; Default background is solid black
  (q/background 0)
  
  ;;;; Make it easier to draw organisms that fit in terrain tiles.
  (q/ellipse-mode :corner)
  
  ;;;; Set color mode to HSB (HSV) instead of default RGB.
  ;;;; Set ranges for these values as well.
  (q/color-mode :hsb 360 100 100 1.0)
  
  ;;;; Don't want outlines on our terrain tiles.
  (q/no-stroke)

  (generate-world r/map-width r/map-height))

(defn update [state]
  (q/background 0)
  (update-world state))

(defn -main [] 
  (q/sketch
     :title      "This... is Eco."
     :size       [r/window-w r/window-h]
     :setup      setup
     :update     update
     :draw       r/environment
     :middleware [m/fun-mode]))

;(q/defsketch eco
  ;:title "This... is Eco."
  ;:size [r/window-w r/window-h]

  ;; setup function called only once, during sketch initialization.
  ;:setup setup

  ;; update is called on each iteration before draw is called.
  ;; It updates sketch state.
  ;:update update

  ;;;;; Draws all active overlays from among terrain, nutrients, and organisms.
  ;;;;; Will eventually allow user control of which overlays to draw.
  ;:draw r/environment

  ;; This sketch uses functional-mode middleware.
  ;; Check quil wiki for more info about middlewares and particularly
  ;; fun-mode.
  ;:middleware [m/fun-mode])
