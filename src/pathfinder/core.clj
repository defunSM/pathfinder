(ns pathfinder.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def block-id (atom 0))
(def boxes (atom []))
(def player (atom {:x 100 :y 100 :c1 150 :c2 150 :c3 150}))
;; (def player-stats (atom {:hp 100}))
(def size (atom 10))


(defn boundaries [])

(defn pause [])

(defn create-box [click-x click-y block-id]
  {:x click-x
   :y click-y
   :mode block-id})

(defn make-box []
  (let [mode (int @block-id)
        c1 (if (= 1 mode)
             (int 100)
             (int 150))
        c2 (if (= 1 mode)
             (int 100)
             (int 230))
        c3 (if (= 1 mode)
             (int 210)
             (int 150))
        new-box {:x (* (Math/floor (/ (q/mouse-x) @size)) @size) :y (* (Math/floor (/ (q/mouse-y) @size)) @size) :c1 c1 :c2 c2 :c3 c3}]
    (if (= (count @boxes) 0)
      (swap! boxes assoc-in [(count @boxes)] new-box)
      (let [valid (if (= new-box (nth @boxes (- (count @boxes) 1)))
                    false
                    true)]
        (if (= valid true)
          (swap! boxes assoc-in [(count @boxes)] new-box))))))

(defn box [x y color1 color2 color3]
  (q/rect x y @size @size)
  (q/stroke 5)
  (q/stroke-weight 2)
  (q/color color1 color2 color3))

(defn setup []
  (q/smooth 10)
  (q/background 220)
  (q/color 240))

(defn mk-box [{:keys [x y c1 c2 c3]}]
  (q/fill c1 c2 c3)
  (q/rect (* (Math/floor (/ x @size)) @size) (* (Math/floor (/ y @size)) @size) @size @size))

(defn mk-player [{:keys [x y c1 c2 c3]}]
  (q/fill c1 c2 c3)
  (q/rect x y @size @size))

(defn validspace [{:keys [x y]}]
  (if (= x (:x @player))
    (if (= y (:y @player))
      (println "ALERT"))))

(defn check-valid-move? []
  (let [player-x (:x @player)
        player-y (:y @player)]
    (doseq [c @boxes]
      (validspace c))))

(defn draw []
  (q/background 220)
  (let [boxes* @boxes]
    (doseq [c boxes*]
      (mk-box c)))

  (mk-player @player)

;;  (check-valid-move?)

  (if (q/key-pressed?)
    (let [key (.toString (q/raw-key))]
      (if (= key "w")
        (if (< 0 (:y @player))
          (do (swap! player update-in [:y] - 5)
              (check-valid-move?))))
      (if (= key "s")
        (if (> (- (q/height) @size) (:y @player))
          (do (swap! player update-in [:y] + 5)
              (check-valid-move?))))
      (if (= key "a")
        (if (< 0 (:x @player))
          (do (swap! player update-in [:x] - 5)
              (check-valid-move?))))
      (if (= key "d")
        (if (> (- (q/width) @size) (:x @player))
          (do (swap! player update-in [:x] + 5)
              (check-valid-move?))))
      (if (= key "t")
        (reset! block-id 1))
      (if (= key "y")
        (reset! block-id 3))
      (if (= key "c")
        (reset! boxes []))
      (if (= key "p")
        (pause))
      (if (= key "z")
        (let [num (count @boxes)]
          (if (> num 0)
            (reset! boxes (pop @boxes))))) ;; could be written to be optimized.
      (if (= key "q")
        (q/exit))
      (if (= key "r")
        (do (q/exit)
            (start-path)))))

  (if (q/mouse-pressed?)
    (make-box))

  (q/fill 255 0 0)
  (q/text (.toString (:hp @player-stats)) (- (q/width) 30) 20)

  (println (q/mouse-x) "-" (q/mouse-y)))

(defn start-path []
  (q/defsketch pathfinder
    :title "Pathfinder"
    :size [500 500]
    :setup setup
    :draw draw
    :mouse-pressed make-box))

(start-path)


  ;; (if (= 3 @block-id)
  ;;   (let [x (* (Math/floor (/ (q/mouse-x) @size)) @size)
  ;;         y (* (Math/floor (/ (q/mouse-y) @size)) @size)]
  ;;     (q/fill 150 230 150)
  ;;     (box x y 200 200 0)))

  ;; (if (= 1 @block-id)
  ;;   (let [x (* (Math/floor (/ (q/mouse-x) @size)) @size)
  ;;         y (* (Math/floor (/ (q/mouse-y) @size)) @size)]
  ;;     (q/fill 100 100 210)
  ;;     (box x y 200 200 0)))


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
