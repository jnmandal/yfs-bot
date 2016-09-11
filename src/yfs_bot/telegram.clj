(ns yfs-bot.telegram
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

;; bot api is documented at: https://core.telegram.org/bots/api

(def bot-id (System/getenv "TG_BOT_TOKEN"))
(def base-url (str "https://api.telegram.org/" "bot" bot-id "/" ))

(defn url
  "helper method to construct urls to the API"
  [method] (str base-url (name method)))

(defn get-updates
  "get the updates currently available for the bot optionally specificy the current offset"
  ([]
   (get-updates nil))
  ([offset]
   (:result (json/parse-string
             (:body (client/get
                     (url :getUpdates)
                     (when
                       offset
                       {:query-params {:offset offset}}))) true))))

(defn send-message
  "send a message to the specified chat"
  ([id message]
   (send-message id message {}))
  ([id message options]
   (client/post
    (url :sendMessage)
    {:content-type :json
     :form-params {:text message
                   :chat_id id
                   :parse_mode (if
                                 (:html options)
                                 "HTML"
                                 "Markdown")}})))

(defn send-action
  "send an action like 'The bot is typing' to the specified chat"
  ([id]
   (send-action id :typing))
  ([id action]
   (client/post
    (url :sendChatAction)
    {:content-type :json
     :form-params {:chat_id id
                   :action action}})))
