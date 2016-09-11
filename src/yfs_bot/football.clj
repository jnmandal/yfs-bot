(ns yfs-bot.football
  (:require [clj-http.client :as client]))

;; https://developer.yahoo.com/fantasysports/guide/
;; the game key for 2016 football is 359

(defn authorization-header [token] {:Authorization (str "Bearer " token)})

(defn get-users-leagues
  [token]
  (client/get
    "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games;game_keys=359/leagues"
    {:headers (authorization-header token)}))

(defn get-users-teams
  [token]
  (client/get
    "https://fantasysports.yahooapis.com/fantasy/v2/users;use_login=1/games;game_keys=359/teams"
    {:headers (authorization-header token)}))
