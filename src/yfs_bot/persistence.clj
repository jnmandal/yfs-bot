(ns yfs-bot.persistence
  (:require [clojure.java.jdbc :as sql]))

(def db-spec {:classname "org.h2.Driver"
              :subprotocol "h2:file"
              :subname (.getCanonicalPath (java.io.File. "db/yfs-bot"))})

;; TODO make this work
(defn table-exist?
  "tells us if the schema exists"
  [] false)

(defn init-db
  "migrates the schema"
  []
  (sql/db-do-commands
   db-spec
   (sql/create-table-ddl :users
                         [[:id "bigint primary key auto_increment"]
                          [:access_token "varchar"]
                          [:refresh_token "varchar"]
                          [:yahoo_guid "varchar"]
                          [:telegram_uid "varchar"]
                          [:slack_uid "varchar"]])))

(defn init-db-if-needed
  "function to set up the DB when the app starts up"
  [] (when (not table-exist?) (init-db)))

(defn insert-user-data
  "saves a user token info"
  [user-data] (sql/insert! db-spec :users user-data))

(defn get-user
  "get a user by their id"
  [id]
  (first
   (sql/query db-spec
              ["select * from users where id = ?" id])))

(defn get-user-by-telegram-id
  "get a user by their telegram uid"
  [tg-id]
  (first
   (sql/query db-spec
              ["select * from users where telegram_uid = ?" tg-id])))

(defn get-user-by-slack-id
  "get a user by their slack uid"
  [slack-id]
  (first
   (sql/query db-spec
              ["select * from users where slack_uid = ?" slack-id])))

(defn update-access-token
  "update the access token for a user based on the regenerate data"
  [user-id token-data]
  (sql/update! db-spec :users
               {:access_token (:access_token token-data)}
               ["refresh_token = ?" (:refresh_token token-data)]))
