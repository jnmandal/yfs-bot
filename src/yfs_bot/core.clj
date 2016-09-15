(ns yfs-bot.core
  (:gen-class)
  (:require [yfs-bot.auth :as auth]
            [yfs-bot.telegram :as tg]
            [yfs-bot.persistence :as db]))

(defn call
  "function that calls another function if it exists"
  [fn-name & args]
  (when (and fn-name (resolve (symbol fn-name)))
    (apply (resolve (symbol fn-name)) args)))

(defn get-command
  [message]
  (let [entity
        (first (filter #(= (:type %) "bot_command") (:entities message)))]
    (when entity
      (subs
       (:text message)
       (inc (:offset entity))
       (+ (:offset entity) (:length entity))))))

(defn handle-update
  "function for handling updates"
  [update]
  (do
    (println update)
    (call (get-command (:message update))
          (:message update))))

(defn start
  "telegram's default start command"
  [message]
  (tg/send-message
   (:id (:from message))
   (str
    "To start, grab a code from [here]("
    auth/auth-url
    ")\nthen send it to me with `/register <code>`")))

(defn register
  "command to register a new user"
  [message]
  (auth/get-token
   (last (clojure.string/split (:text message) #" ")))
  (tg/send-message
   (:id (:from message))
   (str
    "OK, You're authenticated!")))

(defn help
  "command to print some help messages"
  [message]
  (tg/send-message
   (:id (:from message))
   (str
     "/start - start using the bot \n"
     "/register - send us a yahoo code to verify your account \n"
     "/help - get this message \n")))

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
    (let [last-update-id (poll-for-and-process-updates)]
      (when last-update-id (reset! offset (inc last-update-id))))
    (Thread/sleep 800)))
