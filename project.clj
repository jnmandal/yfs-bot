(defproject yfs-bot "0.1.0-SNAPSHOT"
  :description "fantasy football utility bot"
  :url "http://github.com"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-http "2.2.0"]
                 [cheshire "5.6.3"]
                 [org.clojure/java.jdbc "0.6.2-alpha3"]
                 [com.h2database/h2 "1.4.192"]]
  :main ^:skip-aot yfs-bot.core
  :target-path "target/%s"
  :profiles {:dev {:plugins [[lein-dotenv "RELEASE"]]}
             :uberjar {:aot :all}})
