(ns radiohead.core
  (:use overtone.core)
  (:import (java.util Date)))


(def note-duration 1)

(definst sin-inst [note 60 gate 1 note-duration 1 mul 1]
  (let [
        ;env (env-gen (perc note-duration) :gate gate :action 2)
        env (env-gen (envelope [0.1 1 0] [(* 0.01 note-duration) (* 1 note-duration)] :welch) :gate gate :action 2)
        ;env (env-gen (adsr :sustain 0.2) :action 2)
        ]
    (* env
       (+
        (* (sin-osc (midicps note)) mul)
        (*  (sin-osc (midicps (+ 19 note))) 0.08 mul)
        (* (sin-osc (midicps (- note 12))) 0.04 mul)
        )
       0.5)
    )
  )

;(sin-inst (note :C4))

(defn play-arpeggio
  "A function to play a given chord using a given synth and distances.
  The synth must take a midi note as the first argument."
  [& {:keys [root type inversion note-duration order start-at synth delta]
      :or   {root          :C4
             type          :major
             inversion     0
             note-duration 0.2
             order         [0 1 2]
             start-at      (now)
             synth         sin-inst
             delta         nil
             }}]
  (let  [
         sorted-notes (sort (invert-chord (chord root type) inversion))
         notes (map #(nth sorted-notes %) order)
         delta (if (nil? delta)
                 (* note-duration 1000)
                 (* delta 1000))
         offsets (iterate #(+ % delta) start-at)
         ]
    (doall (map (fn [note offset]
                  (println "playing " note " at " offset)
                  (at offset (synth note))
                  )
                notes offsets)
           )
    (nth offsets  (count notes))
    )
                                        ;(+ (* 1000 note-duration (count notes)) (sum offsets) start)

  )
;; (defn play-arpeggio
;;   "A function to play a given chord using a given synth and distances.
;;   The synth must take a midi note as the first argument."
;;   ([root]
;;      (play-arpeggio root :major 0  note-duration  [0 1 2] (now) sin-inst)
;;      )
;;   ([root type]
;;      (play-arpeggio root type 0  note-duration  [0 1 2] (now) sin-inst)
;;      )
;;   ([root type inversion]
;;      (play-arpeggio root type inversion note-duration  [0 1 2] (now) sin-inst)
;;      )
;;   ([root type inversion note-duration]
;;      (play-arpeggio root type inversion note-duration [0 1 2] (now) sin-inst)
;;      )
;;   ([root type inversion note-duration order]
;;      (play-arpeggio root type inversion note-duration order (now) sin-inst)
;;      )
;;   ([root type inversion note-duration order start-at]
;;      (play-arpeggio root type inversion note-duration order start-at sin-inst)
;;      )
;;   ([root type inversion note-duration order start-at synth]
;;                                         ;(if (not (synth? synth))
;;                                         ;(throw (IllegalArgumentException. "function requires a synth"))
;;                                         ;)
;;      (let  [
;;             sorted-notes (sort (invert-chord (chord root type) inversion))
;;             notes (map #(nth sorted-notes %) order)
;;             delta (* note-duration 1000)
;;             ;;start (+ (now) delta)
;;             start (+ start-at delta)
;;             offsets (iterate #(+ % delta) start)
;;             ]
;;        (doall (map (fn [note offset]
;;               (println "playing " note " at " offset)
;;               (at offset (synth note))
;;               )
;;                    notes offsets)
;;               )
;;        (nth offsets  (count notes))
;;        )
;;                                         ;(+ (* 1000 note-duration (count notes)) (sum offsets) start)
;;      )
;;   )

(defn play-notes
  [& {:keys [notes note-duration delta synth start-at mul]
      :or {notes [:C4 :G4] note-duration 0.24 delta 0.2 synth sin-inst start-at (now) mul 1}}]
  (let  [
         delta (* delta 1000)
                                        ;start (+ (now) delta)
                                        ;           start (now)
         offsets (iterate #(+ % delta) start-at)
         ]
    (doall (map (fn [note-name offset]
                  (println note)
                  (at offset (synth (note note-name) :note-duration note-duration :mul mul))
                  )
                notes offsets)
           )
    (nth offsets  (count notes))
    )
  )

(play-notes :notes [:A4 :B3 :D4 :F#6])


;; (defn play-notes
;;   ([notes]
;;      (play-notes notes 1 1 sin-inst (now))
;;      )
;;   ([notes note-duration]
;;      (play-notes notes note-duration note-duration sin-inst (now))
;;      )
;;   ([notes note-duration delta]
;;      (play-notes notes delta note-duration sin-inst (now))
;;      )
;;   ([notes note-duration delta synth start-at]
;;    (let  [
;;             delta (* delta 1000)
;;                                         ;start (+ (now) delta)
;; ;           start (now)
;;             offsets (iterate #(+ % delta) start-at)
;;             ]
;;      (doall (map (fn [note-name offset]
;;                    (println note)
;;                      (at offset (synth (note note-name) :note-duration note-duration))
;;                      )
;;                    notes offsets)
;;               )
;;        (nth offsets  (count notes))
;;        ))
;;      )




(definst env-test [note 60 gate 1]
  (let [
        env (env-gen (perc) :gate gate :action 2)
        ;env2 (env-gen (envelope []) :action 2)
        ]
    (* env (sin-osc (midicps note)))
    )
  )


(def dull-partials
  [
   0.56
   0.92
   1.19
   1.71
   2
   2.74
   3
   3.76
   4.07])
(def partials
  [
   0.5
   1
   3
   4.2
   5.4
   6.8])


(definst b3
  [note 60 a 0.01 d 3 s 1 r 0.01 mul 1]
  (let [freq  (midicps note)
        waves (sin-osc [(* 0.5 freq)
                        freq
                        (* (/ 3 2) freq)
                        (* 2 freq)
                        (* freq 2 (/ 3 2))
                        (* freq 2 2)
                        (* freq 2 2 (/ 5 4))
                        (* freq 2 2 (/ 3 2))
                        (* freq 2 2 2)])
        snd   (apply + waves)
        env (env-gen (envelope [0.7 1 0] [(* 0.03 note-duration) (* 0.9 note-duration)] :welch) :action 2)
                                        ;env   (env-gen (adsr a d s r) :action FREE)]
        ]
    (* env (* mul snd) 0.1)))

(defn play-progression [progression]
  (reduce (fn [offset args]
            (apply offset play-arpeggio (vec
                                         (concat args [:start-at offset])))
            )
          (now)
          progression                                        ;(apply play-arpeggio (vec (conj chord offset)))
          )
  )
;;(play-progression progression)


(defn play-sequences
  (
   [sequences]
   (play-sequences sequences 1 1 sin-inst)
   )
  (
   [sequences note-duration delta synth]
   (reduce (fn [offset sequence]
             (apply play-notes [sequence note-duration delta synth offset])
             )
           (now)
           sequences)
   )
  )


(defcgen bell-partials
  "Bell partial generator"
  [freq {:default 440 :doc "The fundamental frequency for the partials"}
   dur  {:default 1.0 :doc "Duration multiplier. Length of longest partial will
                            be dur seconds"}
   partials {:default [0.5 1 2 4] :doc "sequence of frequencies which are
                                        multiples of freq"}]
  "Generates a series of progressively shorter and quieter enveloped sine waves
  for each of the partials specified. The length of the envolope is proportional
  to dur and the fundamental frequency is specified with freq."
  (:ar
   (apply +
          (map
           (fn [partial proportion]
             (let [env      (env-gen (perc 0.01 (* dur proportion)))
                   vol      (/ proportion 2)
                   overtone (* partial freq)]
               (* env vol (sin-osc overtone))))
           partials ;; current partial
           (iterate #(/ % 2) 1.0)  ;; proportions (1.0  0.5 0.25)  etc
           ))))


(definst dull-bell [freq 220 dur 1.0 amp 1.0]
  (let [snd (* amp (bell-partials freq dur dull-partials))]
    (detect-silence snd :action FREE)
    snd))

(definst pretty-bell [note 60 dur 1.0 mul 1.0]
  (let [
        freq (midicps note)
        snd (* mul (bell-partials freq dur partials))
        ]
    (detect-silence snd :action FREE)
    snd))

;(pretty-bell 6 2)


;(play-arpeggio :A4)
;(kill sin-inst)
                                        ;(sin-inst)
;; (def progression
;;   [[:root :D4 :type :major :inversion 1 :note-duration 0.25 :order (flatten (repeat 11 [2 1 0])) :synth pretty-bell]
;;    [:root :A4 :type :major :inversion 0 :note-duration 0.25 :order (flatten (repeat 11 [2 1 0])) :synth pretty-bell]
;;    [:root :B4 :type :minor :inversion 0 :note-duration 0.25 :order (flatten (repeat 11 [2 1 0])) :synth pretty-bell]
;;    ])
