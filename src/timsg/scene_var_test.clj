(ns timsg.scene-var-test
  (:use clojure.repl clojure.pprint
        arcadia.core arcadia.linear)
  (:require [timsg.imperative :as i]
            [timsg.scene-var :as sv])
  (:import [UnityEngine Transform GameObject Component Rigidbody]))

(sv/defgetter floor
  ([]
   (let [flr (create-primitive :cube)]
     (set! (.name flr) "the-floor")
     flr))
  ([flr]
   (with-cmpt flr [tr Transform]
     (i/sets! tr
       position (v3 0 -0.5 0)
       localScale (v3 10 1 10)))))
;; We can make it rotate:

;; CHANGE THIS when the signature of hook+ changes!
(defn rotate-floor [flr]
  (with-cmpt flr [tr Transform]
    (i/set-with! tr [r rotation]
      (qq* (aa 1 0 1 0) r))))

(sv/defgetter floor
  ([]
   (let [flr (create-primitive :cube)]
     (set! (.name flr) "the-floor")
     flr))
  ([flr]
   (with-cmpt flr [tr Transform]
     (i/sets! tr
       position (v3 0 -0.5 0)
       localScale (v3 10 1 10)))
   (hook+ flr :update ::rotate #'rotate-floor)))
