(ns radiohead.arpeggi
  (:use radiohead.core
        overtone.core)
  )

;(connect-external-server 57110)

(def progression1
  [[:D4 :major 1 0.25 (flatten (repeat 11 [2 1 0])) pretty-bell]
   [:A4 :major 0 0.25 (flatten (repeat 11 [2 1 0])) pretty-bell]
   [:B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0])) pretty-bell]
   ])

(def ocarina-phrase [:E4 :A4 :C#5 :B4 :G4 :F#4 :D5])
(def phrase1 [:D5 :G4 :E4])
(def phrase2 [:E5 :A4 :F#4])
(def phrase3 [:A5 :C#5 :A4])
(def phrase4 [:F#5 :E4 :G4])


(let
    [start (now)
     delta 0.4
     note-duration 0.2
     notes ocarina-phrase
     ]
  (dotimes [i 3]
    (apply-at (+ start (* i (* note-duration (count notes) 1000))) play-notes [:notes (map note notes) :note-duration note-duration :delta delta])
    )
  )

(defn repeat-phrases
  ([phrases repetitions]
   (repeat-phrases phrases repetitions sin-inst 1)
   )
  ([phrases repetitions synth]
   (repeat-phrases phrases repetitions synth 1)
   )
  ([phrases repetitions synth mul]
    (let [
          repeating-phrase (flatten
                             (map (fn [phrase repetition]
                                    (repeat repetition phrase)
                                    )
                                  phrases repetitions
                                  ))
          ]
      (apply play-notes [:notes repeating-phrase :synth synth :mul mul])
      ))
  )


(play-notes :notes ocarina-phrase :synth pretty-bell)
(play-notes :notes phrase1 :synth pretty-bell)
(play-notes :notes phrase2 :synth sin-inst)
(play-notes :notes phrase3 :synth sin-inst)
(play-notes :notes phrase4 :synth sin-inst)

(repeat-phrases [ocarina-phrase] [3] sin-inst)

(repeat-phrases [ocarina-phrase] [3] pretty-bell)
(repeat-phrases [phrase1 phrase2 phrase3 phrase4] [2 2 2 2] b3 0.4)

; Play both parts together
(doall
  (repeat-phrases [ocarina-phrase] [19] sin-inst)
  (repeat-phrases [phrase1 phrase2 phrase3 phrase4] [11 11 11 11] b3 0.4))

(play-arpeggio :root :D4 :type :major :inversion 1 :note-duration 0.2 :order (flatten (repeat 3 [2 1 0])) :synth sin-inst)
(play-arpeggio :root :A4 :type :major :inversion 0 :note-duration 0.2 :order (flatten (repeat 3 [2 1 0])) :synth sin-inst)
(play-arpeggio :root :D5 :type :major :inversion 0 :note-duration 0.2 :order (flatten (repeat 3 [2 1 0])) :synth sin-inst)
(play-arpeggio :root :B4 :type :minor :inversion 0 :note-duration 0.2 :order (flatten (repeat 3 [2 1 0])) :synth sin-inst)


;; (let
;;     [start (now)
;;      delta 0.4
;;      note-duration 0.2
;;      notes ocarina-phrase
;;      ]
;;   (dotimes [i 3]
;;     (apply-at (+ start (* i (* note-duration (count notes) 1000))) play-notes [(map note notes) note-duration delta])
;;     )
;;   )

;; (defn repeat-phrases [phrases repetitions]
;;   (let [
;;         repeating-phrase (map (fn [phrase repetition]
;;                                 (repeat repetition phrase)
;;                                 )
;;                               phrases repetitions
;;                               )
;;         ]
;;     (apply play-notes [(flatten repeating-phrase) 0.2 0.2 b3 (now)])
;;     )
;;   )



;; (play-arpeggio :D4 :major 1 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (play-arpeggio :A4 :major 0 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (play-arpeggio :B4 :minor 0 0.25 (flatten (repeat 11 [2 1 0])) (now))
;; (stop)
