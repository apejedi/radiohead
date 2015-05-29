(ns radiohead.arpeggi
  (:use radiohead.core
        overtone.core)
  )

(def progression1
  [[:D4 :major 1 0.25 (flatten (repeat 11 [2 1 0]))]
   [:A4 :major 0 0.25 (flatten (repeat 11 [2 1 0]))]
   [:B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0]))]
   ])


(defn play-progression [progression]
    (reduce (fn [offset chord]
              (println "playing " chord  " at " offset)
              (apply play-arpeggio (vec (conj chord offset)))
              )
            (now)
            progression)
  )


(defn play-second []

  )

;(play-progression progression1)


(let [
      start (now)
      delta 0.4
      note-duration 0.2
      notes phrase2
      ]
  (dotimes [i 5]
                                        ;(apply-at (+ start (* i (* delta 6 1000))) play-notes [(map note [:G6 :A6 :C#7 :B6 :A6 :D7]) 2 delta pretty-bell])
    (apply-at (+ start (* i (* note-duration (count notes) 1000))) play-notes [(map note notes) note-duration delta])
    )
  )

(def phrase2 [:G4 :A4 :C#5 :B4 :A4 :D5])
(def phrase [:E4 :A4 :C#5 :B4 :G4 :F#4 :D5])

;; (apply play-notes [(map note phrase) 0.2 0.4])

;; (play-arpeggio :D4 :major 1 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (play-arpeggio :A4 :major 0 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (play-arpeggio :B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (stop)
