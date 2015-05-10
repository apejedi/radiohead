(ns radiohead.core
  (:use overtone.core)
  (:import (java.util Date))
  )

(definst sin-inst [note 60 gate 1]
           (let [
                 env (env-gen:kr (perc 0.5) :gate gate :action 2)
                 ]
             (* env (sin-osc (midicps note)))
             )
           )
(defn play-arpeggio
  "A function to play a given chord using a given synth and distances.
  The synth must take a midi note as the first argument."
  ([root]
     (play-arpeggio root :major 0  0.5  [0 1 2])
     )
  ([root type]
     (play-arpeggio root type 0  0.5  [0 1 2])
     )
  ([root type inversion]
     (play-arpeggio root type inversion 0.5  [0 1 2])
     )
  ([root type inversion note-duration]
     (play-arpeggio root type inversion note-duration [0 1 2])
     )
  ([root type inversion note-duration order start-at]
                                        ;(if (not (synth? synth))
                                        ;(throw (IllegalArgumentException. "function requires a synth"))
                                        ;)
     (let  [
            sorted-notes (sort (invert-chord (chord root type) inversion))
            notes (map #(nth sorted-notes %) order)
            delta (* note-duration 1000)
            ;;start (+ (now) delta)
            start (+ start-at delta)
            offsets (iterate #(+ % delta) start)
            ]
       (doall (map (fn [note offset]
              (println "playing " note " at " offset)
              (at offset (sin-inst note))
              )
                   notes offsets)
              )
       (nth offsets  (count notes))
       )
                                        ;(+ (* 1000 note-duration (count notes)) (sum offsets) start)
     )
  )

;(play-arpeggio :A4)
;(kill sin-inst)
;(sin-inst)
