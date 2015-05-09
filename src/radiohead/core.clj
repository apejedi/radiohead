(ns radiohead.core
  (:use overtone.core)
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
  ([synth root]
     (play-arpeggio synth root :major 0 [] 1)
     )
  ([synth root type]
     (play-arpeggio synth root type 0 [] 1)
     )
  ([synth root type inversion]
     (play-arpeggio synth root type inversion [] 1)
   )
  ([synth root type inversion distances note-duration]
     ;(if (not (synth? synth))
       ;(throw (IllegalArgumentException. "function requires a synth"))
       ;)
     (let  [
            distances (cond
                       (and (seq? distances) (> (count distances) 0)) distances
                       :else [500]
                       )
            note1 (first (invert-chord (chord root type) inversion))
            notes (rest (invert-chord (chord root type) inversion))
            start (now)
            ]
       (sin-inst note1)
       (loop [notes notes d distances now start]
         (at (+ now (first d)) (sin-inst (first notes)))
         (if (not (= nil (seq (rest notes))))
           (recur
            (vec (rest notes))
            (if (not (= nil (seq (rest d)))) (vec (rest d)) [500])
            (+ now (first d))
            )
           )
         )
       )
     )
  )
