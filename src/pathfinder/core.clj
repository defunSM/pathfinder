(ns pathfinder.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def typing (atom ""))
(def block-id (atom 0))
(def boxes (atom []))

(defn create-box [click-x click-y block-id]
  {:x click-x
   :y click-y
   :mode block-id})

(defn make-box []
  (swap! boxes assoc-in [(count @boxes)] {:x (q/mouse-x) :y (q/mouse-y) :mode @block-id}))

(defn box [x y color1 color2 color3]
  (q/rect x y 50 50)
  (q/stroke 5)
  (q/stroke-weight 2)
  (q/color color1 color2 color3))


(defn setup []
  (q/smooth 10)
  (q/background 220)
  (q/color 240))

(defn mk-box [{:keys [x y mode]}]
  (let [color1 (= mode 1)]
    (q/rect (* (Math/floor (/ x 50)) 50) (* (Math/floor (/ y 50)) 50) 50 50 0)))

(defn draw []
  (q/background 220)
  (let [boxes* @boxes]
    (doseq [c boxes*]
      (mk-box c)))

  (if (= 3 @block-id)
    (let [x (* (Math/floor (/ (q/mouse-x) 50)) 50)
          y (* (Math/floor (/ (q/mouse-y) 50)) 50)]
      (q/fill 150 230 150)
      (box x y 200 200 0)))

  (if (= 1 @block-id)
    (let [x (* (Math/floor (/ (q/mouse-x) 50)) 50)
          y (* (Math/floor (/ (q/mouse-y) 50)) 50)]
      (q/fill 100 100 210)
      (box x y 200 200 0)))

  (if (q/key-pressed?)
    (do (if (= (.toString (q/raw-key)) "s")
          (reset! block-id 1))
        (if (= (.toString (q/raw-key)) "e")
          (reset! block-id 3))))

  (if (q/mouse-pressed?)
    (make-box))

  (q/fill 255 0 0)
  (q/text (.toString (q/seconds)) (- (q/width) 30) 20))

(q/defsketch pathfinder
  :title "SMchat"
  :size [500 500]
  :setup setup
  :draw draw
  :mouse-pressed make-box)

  ;; (loop [x 0
  ;;        y 0
  ;;        color1 200
  ;;        color2 200
  ;;        color3 0]
  ;;   (if (> 455 x)
  ;;     (do (q/fill 100 0 100)
  ;;         (box x y color1 color2 color3)
  ;;         (recur (+ x 50) y color1 color2 color3))
  ;;     (if (> 455 y)
  ;;       (do (box x y color1 color2 color3)
  ;;           (recur 0 (+ 50 y) color1 color2 color3)))))

  ;; (for [boxes* @boxes]
  ;;   (do (q/fill 100 100 210)
  ;;       (box (:x (second boxes*)) (:y (second boxes*))
  ;;            (let [color1 (if (= 1 (:mode boxes*)) (int 100))
  ;;                  color1 (if (= 3 (:mode boxes*)) (int 150))]
  ;;              color1)
  ;;            (let [color2 (if (= 1 (:mode boxes*)) (int 100))
  ;;                  color2 (if (= 3 (:mode boxes*)) (int 230))]
  ;;              color2)
  ;;            (let [color3 (if (= 1 (:mode boxes*)) (int 210))
  ;;                  color3 (if (= 3 (:mode boxes*)) (int 150))]
  ;;              color3))))

;; make this work

  ;; (if (= 0 (count @boxes))
  ;;   (println "none")
  ;;   (let [num-of-boxes (count @boxes)
  ;;         box-info ((keyword (str num-of-boxes)) @boxes)]
  ;;     (loop [x num-of-boxes]
  ;;       (if (> (count @boxes) x)
  ;;         (do (let [color1 (if (= 1 (:mode box-info)) (int 100))
  ;;                   color1 (if (= 3 (:mode box-info)) (int 150))
  ;;                   color2 (if (= 1 (:mode box-info)) (int 100))
  ;;                   color2 (if (= 3 (:mode box-info)) (int 230))
  ;;                   color3 (if (= 1 (:mode box-info)) (int 210))
  ;;                   color3 (if (= 3 (:mode box-info)) (int 150))]
  ;;               (box (:x box-info) (:y box-info) color1 color2 color3)
  ;;               (println "This is working.")
  ;;               (recur (inc x)))))))))

  ;; (if (= 0 (count @boxes))
  ;;   (println "NONE")))
