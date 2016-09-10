(defproject yfs-bot "0.1.0-SNAPSHOT"
  :description "fantasy football utility bot"
  :url "http://github.com"
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot yfs-bot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
