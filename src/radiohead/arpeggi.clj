(ns radiohead.arpeggi
  (:use radiohead.core
        overtone.core)
  )

(def progression
  [[:D4 :major 1]
   [:A4 :major 0]
   [:B4 :minor 0]
   ])

(dotimes [i ]
(map #(apply-at
   (+ (now) (* 1000 %2))
   play-arpeggio (flatten [sin-osc %1]))
     progression (range (count progression))
  )
