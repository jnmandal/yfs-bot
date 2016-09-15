(ns yfs-bot.core
  (:gen-class)
  (:require [yfs-bot.auth :as auth]
            [yfs-bot.telegram :as tg]
            [yfs-bot.persistence :as db]))

(defn handle-update
  [update]
  (println update))

;; |  Λ--Λ--Λ--Λ--Λ--Λ  |
;; | [o][o][o][o][o][o] |
;; | [o][o][χ][χ][o][o] |
;; | [o][o][χ][χ][o][o] |
;; V  there be dragons  V

(def offset (atom -1)) ;; global shared state for tg polling

(defn handle-updates
  "handle the updates and then return the last update_id"
  [updates]
  (let [last-update-id (last updates)]
    (when updates
     (doseq [update updates] (handle-update update))
     (:update_id last-update-id))))

(defn poll-for-and-process-updates
  "poll for updates and handle them, reseting the offset"
  []
  (handle-updates
   (tg/get-updates @offset)))

(defn -main
  "run the main loop"
  [& args]
  (while true
   (do
    (println @offset)
    (let [last-update-id (poll-for-and-process-updates)]
      (when last-update-id (reset! offset (inc last-update-id)))))
    (Thread/sleep 800)))
