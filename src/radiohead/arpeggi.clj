(ns radiohead.arpeggi
  (:use radiohead.core
        overtone.core)
  )

(def progression1
  [[:D4 :major 1 0.25 (flatten (repeat 11 [2 1 0]))]
   [:A4 :major 0 0.25 (flatten (repeat 11 [2 1 0]))]
   [:B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0]))]
   ])

(def ocarina-phrase [:E4 :A4 :C#5 :B4 :G4 :F#4 :D5])
(def phrase1 [:D5 :G4 :E4])
(def phrase2 [:E5 :A4 :F#4])
(def phrase3 [:A5 :C#5 :A4])
(def phrase4 [:F#5 :E4 :G4])
(play-arpeggio :D4 :major 1 0.25 (flatten (repeat 11 [2 1 0])))
(play-arpeggio :A4 :major 0 0.25 (flatten (repeat 11 [2 1 0])))
(play-arpeggio :B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0])))




(defn play-second []

  )

;(play-progression progression1)


(let
    [start (now)
     delta 0.4
     note-duration 0.2
     notes ocarina-phrase
     ]
  (dotimes [i 20]
                                        ;(apply-at (+ start (* i (* delta 6 1000))) play-notes [(map note [:G6 :A6 :C#7 :B6 :A6 :D7]) 2 delta pretty-bell])
    (apply-at (+ start (* i (* note-duration (count notes) 1000))) play-notes [(map note notes) note-duration delta])
    )
  )

(def repetitions [4 4])

(play-sequences [phrase1 phrase2] 0.2 0.2 b3)

(repeat-phrases [phrase1 phrase2 phrase3 phrase4] [11 11 11 11])

(defn repeat-phrases [phrases repetitions]
  (let [
        repeating-phrase (map (fn [phrase repetition]
                              (repeat repetition phrase)
                              )
                              phrases repetitions
                              )
        ]
        (apply play-notes [(flatten repeating-phrase) 0.2 0.2 b3 (now)])
    )
  )


(play-notes [:D4 :A4])



;(def phrase2 [:G4 :A4 :C#5 :B4 :A4 :D5])

(def )

;; (apply play-notes [(map note phrase) 0.2 0.4])

;; (play-arpeggio :D4 :major 1 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (play-arpeggio :A4 :major 0 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (play-arpeggio :B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (stop)
