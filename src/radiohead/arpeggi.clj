(ns radiohead.arpeggi
  (:use radiohead.core
        overtone.core)
  )

(def progression
  [[:D4 :major 1]
   [:A4 :major 0]
   [:B4 :minor 0]
   ])

(doseq [chord progression]
  (apply play-arpeggio chord)
  )


(defn play-progression [progression]
  (let [start (now)
        delta 4000
        offsets (iterate #(+ % delta) start)
        ]
    (map (fn [chord offset]
           (println "playing " chord " at " offset)
           (at offset (apply play-arpeggio chord)))
         progression offsets)
    )
  )
(play-progression progression)

(play-arpeggio :D4 :major 1)
(play-arpeggio :A4 :major 0)
(play-arpeggio :B4 :minor 0)



;; (dotimes [i ]
;; (map #(apply-at
;;    (+ (now) (* 1000 %2))
;;    play-arpeggio (flatten [sin-osc %1]))
;;      progression (range (count progression))
;;   )
