(ns radiohead.core
  (:use overtone.core)
  (:import (java.util Date))
  )


(defn play-arpeggio
  "A function to play a given chord using a given synth and distances.
  The synth must take a midi note as the first argument."
  ([root]
     (play-arpeggio root :major 0  0.5 (now))
     )
  ([root type]
     (play-arpeggio root type 0  0.5 (now))
     )
  ([root type inversion]
     (play-arpeggio root type inversion 0.5 (now))
     )
  ([root type inversion note-duration]
     (play-arpeggio root type inversion note-duration (now))
   )
  ([root type inversion note-duration start-at]
     ;(if (not (synth? synth))
       ;(throw (IllegalArgumentException. "function requires a synth"))
       ;)
     (let  [
            notes (invert-chord (chord root type) inversion)
            delta (* note-duration 1000)
            start (+ (now) delta)
            offsets (iterate #(+ % delta) start)
            ]
       (map (fn [note offset]
              (println "playing " note " at " offset)
              (at offset (sin-inst note))
              )
            notes offsets)
       )
     )
  )
(definst sin-inst [note 60 gate 1]
           (let [
                 env (env-gen:kr (perc 0.5) :gate gate :action 2)
                 ]
             (* env (sin-osc (midicps note)))
             )
           )
;(play-arpeggio :A4)
;(kill sin-inst)
