(ns yfs-bot.auth
  (:require [clj-http.client :as client]
            [cheshire.core :as json]))

;; using the authentication stategy described on this guide (there are several others)
;; https://developer.yahoo.com/oauth2/guide/openid_connect/getting_started.html#getting-started-auth-code

(def client-id
  (System/getenv "CLIENT_ID"))

(def client-secret
  (System/getenv "CLIENT_SECRET"))

(def auth-url
  (str "https://api.login.yahoo.com/oauth2/request_auth?"
       (client/generate-query-string {:response_type "code"
                                      :redirect_uri "oob"
                                      :client_id client-id})))

(defn get-token
  "using the users yahoo-provided code, fetch a token"
  [code]
  (json/parse-string (:body
    (client/post "https://api.login.yahoo.com/oauth2/get_token"
                 {:content-type :json
                  :form-params {:client_id client-id
                                :client_secret client-secret
                                :grant_type "authorization_code"
                                :redirect_uri "oob"
                                :code code}})) true))

(defn regenerate-token
  "using the stored refresh-token from the users first auth, fetch a token"
  [refresh-token]
  (json/parse-string (:body
    (client/post "https://api.login.yahoo.com/oauth2/get_token"
                 {:content-type :json
                  :form-params {:client_id client-id
                                :client_secret client-secret
                                :grant_type "refresh_token"
                                :redirect_uri "oob"
                                :refresh_token refresh-token}})) true))
