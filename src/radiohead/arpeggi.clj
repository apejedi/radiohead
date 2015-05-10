(ns radiohead.arpeggi
  (:use radiohead.core
        overtone.core)
  )

(def progression1
  [[:D4 :major 1 0.25 (flatten (repeat 11 [2 1 0]))]
   [:A4 :major 0 0.25 (flatten (repeat 11 [2 1 0]))]
   [:B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0]))]
   ])

(doseq [chord progression]
  (apply play-arpeggio chord)
  )


(defn play-progression [progression]
    (reduce (fn [offset chord]
              (println "playing " chord  " at " offset)
              (apply play-arpeggio (vec (conj chord offset)))
              )
            (now)
            progression)
    ;; (map (fn [chord]
    ;;        (println "playing " chord)
    ;;        (at offset (apply play-arpeggio chord))
    ;;        )
    ;;      progression)
  )

(play-progression progression1)

(play-arpeggio :D4 :major 1 0.25 (flatten (repeat 11 [2 1 0])) (now))
(play-arpeggio :A4 :major 0 0.25 (flatten (repeat 11 [2 1 0])) (now))
(play-arpeggio :B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0])) (now))
(stop)


;; (dotimes [i ]
;; (map #(apply-at
;;    (+ (now) (* 1000 %2))
;;    play-arpeggio (flatten [sin-osc %1]))
;;      progression (range (count progression))
;;   )
